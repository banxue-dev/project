package com.banxue.qrcode.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.banxue.qrcode.entity.Car;
import com.banxue.qrcode.entity.Card;
import com.banxue.qrcode.entity.User;
import com.banxue.qrcode.service.ICarService;
import com.banxue.qrcode.service.ICardService;
import com.banxue.qrcode.service.IUserService;
import com.banxue.utils.Constants;
import com.banxue.utils.R;
import com.banxue.utils.StringUtils;
import com.banxue.utils.file.FileUtil;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.sms.SendShortMessage;
import com.banxue.utils.wx.WxConstants;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author feng
 * @since 2018-09-27
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	@Autowired
	private ICardService cardService;

	@Autowired
	private ICarService carService;
	@Value("${file.path}")
	private static String uploadPath;
	@Value("${spring.profiles.active}")
	private static String active;
	@PostMapping("/modUser")
	@ResponseBody
	public R modUser(String message, String nickname, String openId,String headName) {
		try {
			User u = userService.getUserByOpenId(openId);
			u.setNickName(nickname);
			u.setUserMessage(message);
			u.setUserHeadUrl(headName);
			u.setUserPhone(null);
			userService.updateById(u);
			return R.ok();
		} catch (Exception e) {
			FileLog.errorLog(e, "更新用户信息异常。");
		}
		return R.error();
	}

	@ResponseBody
	@PostMapping("/getUser")
	public R getUser(String openId) {
		try {
			User u = userService.getUserByOpenId(openId);
			User ru = new User();
			ru.setUserMessage(u.getUserMessage());
			ru.setNickName(u.getNickName());
			ru.setUserHeadUrl(u.getUserHeadUrl());
			return R.okdata(ru);
		} catch (Exception e) {
			FileLog.errorLog(e, "更新用户信息异常。");
		}
		return R.error();
	}
	@ResponseBody
	@PostMapping("/getVCode")
	public R getVCode(String openId,HttpServletRequest request,String telephone) {
		try {
			
			HttpSession session= request.getSession();
			Long newTime=new Date().getTime();
			try {
				String lastTime=(String) session.getAttribute(Constants.CODETIMEKEY);
				if(lastTime!=null && lastTime!="") {
					long cha=(newTime-Long.parseLong(lastTime))/1000;
					if(cha<Constants.VCODTIMEOUT) {
						//未超时，不予获取
						return R.error("请"+cha+"秒后再试");
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			String vcode="";
			for(int i=0;i<Constants.VCODELENGTH;i++) {
				Random rms=new Random();
				vcode=vcode+rms.nextInt(10);
			}
			session.setAttribute(Constants.ValidteCodeName, vcode);
			session.setAttribute(Constants.CODETIMEKEY, new Date().getTime()+"");
			if(false)  {
				HashMap<String, Object> result=SendShortMessage.sendMess(telephone, vcode, "330663", "3");
				if("000000".equals(result.get("statusCode"))){
					//正常返回输出data包体信息（map）
					return R.ok();
				}else{
					//异常返回输出错误码和错误信息
					R.error(result.get("statusMsg").toString());
				}
			}
			FileLog.debugLog(vcode);
			return R.ok();
		} catch (Exception e) {
			FileLog.errorLog(e, "获取验证码异常。");
		}
		return R.error();
	}
	@PostMapping(value="/modCarBind",produces = "application/json;charset=UTF-8")
	@ResponseBody()
	public R modCarBind(HttpServletRequest request, String openid,String telephone,String carNo,String validCode) {
		try{
			HttpSession session= request.getSession();
			String scode=(String) session.getAttribute(Constants.ValidteCodeName);
			FileLog.debugLog(scode+"-"+validCode);
			if(!StringUtils.twoStrMatch(scode, validCode)) {
				return R.error("验证码错误。");
			}
			if(StringUtils.StrFilter(carNo,openid,telephone) ) {
				return R.error("包含非法字符。");
			}
			Wrapper<User> wus=new EntityWrapper<User>();
			wus.addFilter("wx_opend_id", openid);
			wus.addFilter("user_phone", telephone);
			User u=userService.selectOne(wus);
			if(u==null) {
				return R.error("错误的用户。");
			}
			Wrapper<Car> wra=new EntityWrapper<Car>();
			wra.eq("car_no", carNo);
			Car car=carService.selectOne(wra);
			if(car==null) {
				return R.error("错误的车牌号");
			}
			car.setCarNo(carNo);
			carService.updateById(car);
			return R.ok("成功");
		}catch(Exception e) {
			FileLog.errorLog(e);
		}
		return R.error();
	}
	@ResponseBody
	@PostMapping(value = "/uploadHead", produces = "application/json;charset=UTF-8")
	public R uploadHead(HttpServletRequest request) {
		String fname = "";
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
				String key = (String) it.next();
				MultipartFile mulfile = multipartRequest.getFile(key);
//				fname = FileUtil.saveFile2(multipartRequest, mulfile);
				fname = FileUtil.saveFile1( mulfile);
				FileLog.debugLog(fname);
			}
			return R.ok("成功",fname);
		} catch (Exception e) {
			FileLog.errorLog(e, "上传头像失败");
		}
		return R.error();
	}

	@RequestMapping(value = "/getHead")
	public void getHead(HttpServletRequest request, HttpServletResponse response, String headName) {
		if (StringUtils.isNullString(headName)) {
			headName = "zanwu.jpg";
		}
		File f = new File(uploadPath == null ? "c:/data/head/"+headName : uploadPath + "/"+headName);
		try {
			FileInputStream inputStream = new FileInputStream(f);
			byte[] data = new byte[(int) f.length()];
			inputStream.read(data);
			inputStream.close();
			response.setContentType("image/png");

			OutputStream stream = response.getOutputStream();
			stream.write(data);
			stream.flush();
			stream.close();
		} catch (IOException e) {
			FileLog.errorLog(e,"获取图片异常");
		}
	}

	@RequestMapping(value = "/cardBindCar",produces = "application/json;charset=UTF-8")
	@ResponseBody()
	public R cardBindCar(HttpServletRequest request, String carNo, String validCode,String userPhone,String cardNo) {
		try {
			if(StringUtils.RequestParmsV(cardNo,carNo,validCode,userPhone)) {
				return R.error("错误的参数。");
			}
			HttpSession session= request.getSession();
			String scode=(String) session.getAttribute(Constants.ValidteCodeName);
			if(!StringUtils.twoStrMatch(scode, validCode)) {
				return R.error("验证码错误。");
			}
			Wrapper<Card> wra=new EntityWrapper<Card>();
			wra.addFilter("card_no", cardNo);
			Card card=cardService.selectOne(wra);
			if(card==null) {
				return R.error("错误的卡号。");
			}

			Wrapper<Car> wa=new EntityWrapper<Car>();
			wa.addFilter("car_no", carNo);
			Car car=carService.selectOne(wa);
			if(car==null) {
				car=new Car();
				car.setCardNo(cardNo);
				car.setCarNo(carNo);
				car.setUserPhone(userPhone);
				carService.insert(car);
			}else {
				if(!StringUtils.isNullString(car.getUserPhone())) {
					//如果这辆车已经绑定了用户，就不允许此次操作。
					return R.error("此车已绑定用户。");
				}
			}
			Wrapper<User> wua=new EntityWrapper<User>();
			wa.addFilter("user_phone", userPhone);
			User u=userService.selectOne(wua);
			if(u==null) {
				//没有用户，注册一个
				u=new User();
				u.setUserPhone(userPhone);
				u.setUserMessage("若需要挪车，请电话联系。");
				u.setNickName("挪车码用户");
				userService.insert(u);
			}
			card.setUserPhone(userPhone);
			cardService.updateById(card);
			return R.ok();
		} catch (Exception e) {
			FileLog.errorLog(e,"绑定车辆异常");
		}
		return R.error("异常。");
	}
	@PostMapping("/sendMes")
	@ResponseBody
	public R sendMessageToWxUser(String cardNo,String carNo) {
		try {
			if(StringUtils.RequestParmsV(cardNo,carNo)) {
				return R.error("错误的车牌号。");
			}
			Wrapper<Car> wra=new EntityWrapper<Car>();
			wra.addFilter("car_no", carNo);
			wra.addFilter("card_no", cardNo);
			Car car=carService.selectOne(wra);
			if(car==null || StringUtils.isNullString(car.getUserPhone())) {
				return R.error("错误的车牌号");
			}
			Wrapper<User> wus=new EntityWrapper<User>();
			wus.addFilter("user_phone", car.getUserPhone());
			User u=userService.selectOne(wus);
			if(u==null) {
				return R.error("用户还未绑定");
			}
			if(StringUtils.isNullString(u.getWxOpendId())) {
				return R.error("该用户还未绑定微信");
			}
			String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
			String token = WxConstants.WxToken;
			
		} catch (Exception e) {

		}
		return R.error("异常");
	}
	@PostMapping("/callUserPhone")
	@ResponseBody
	public R callUserPhone(String cardNo,String carNo) {
		try {
			if(StringUtils.RequestParmsV(cardNo,carNo)) {
				return R.error("错误的车牌号");
			}
			Wrapper<Car> wra=new EntityWrapper<Car>();
			wra.addFilter("car_no", carNo);
			wra.addFilter("card_no", cardNo);
			Car car=carService.selectOne(wra);
			if(car==null || StringUtils.isNullString(car.getUserPhone())) {
				return R.error("错误的车牌号");
			}
			return R.okdata("13524954089");
		}catch(Exception e) {
			
		}
		return R.error();
	}
}
