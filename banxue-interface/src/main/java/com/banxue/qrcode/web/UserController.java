package com.banxue.qrcode.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.banxue.qrcode.entity.User;
import com.banxue.qrcode.service.IUserService;
import com.banxue.utils.Constants;
import com.banxue.utils.R;
import com.banxue.utils.StringUtils;
import com.banxue.utils.file.FileUtil;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.sms.SendShortMessage;

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
			if(!StringUtils.twoStrMatch(active, "prod"))  {
				HashMap<String, Object> result=SendShortMessage.sendMess(telephone, vcode, "330663", "3");
				if("000000".equals(result.get("statusCode"))){
					//正常返回输出data包体信息（map）
					return R.ok();
				}else{
					//异常返回输出错误码和错误信息
					R.error(result.get("statusMsg").toString());
				}
			}
		} catch (Exception e) {
			FileLog.errorLog(e, "获取用户信息异常。");
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

	@RequestMapping(value = "/uploadHead")
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
			e.printStackTrace();
		}
	}
}
