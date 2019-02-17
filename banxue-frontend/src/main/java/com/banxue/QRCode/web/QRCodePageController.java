package com.banxue.QRCode.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.banxue.QRCode.entity.Car;
import com.banxue.QRCode.entity.Card;
import com.banxue.QRCode.entity.User;
import com.banxue.QRCode.service.ICarService;
import com.banxue.QRCode.service.ICardService;
import com.banxue.QRCode.service.IUserService;
import com.banxue.utils.Constants;
import com.banxue.utils.R;
import com.banxue.utils.StringUtils;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.pay.wex.ServiceUtil;
import com.banxue.utils.wx.WxConstants;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

/**
 * 作者：fengchase 时间：2018年9月26日 文件：QRCodePageController.java 项目：banxue-frontend
 */
@Controller
@RequestMapping("/qrcode")
public class QRCodePageController {

	@Autowired
	private IUserService userService;
	@Autowired
	private ICardService cardService;
	@Autowired
	private ICarService carService;
	@Value("${spring.profiles.active}")
	private String active;
	@Value("${HeadUrlHead}")
	private String HeadUrlHead;
	@GetMapping("/my")
	public String toWodePage(HttpServletRequest request) {
		try {
			/**
			 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=http%3A%2F%2Fwww.banxue.fun%2Fqrcode%2Fmy&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
			 * 逻辑 1.获取到code后获取用户的openid，然后去后台查询此openid是否绑定了手机号。http%3A%2F%2Fwww.banxue.fun%2Fqrcode%2Fmy
			 * 2.如果没有绑定或者后台没有保存过这条openId，直接跳转到绑定页面 。 3.如果后台没有保存过这条，则将此条用户入库。
			 * 4.将此openid传入前台用于绑定。
			 */
			// 获取用户id
			String wxcode = request.getParameter("code");
			String openidparam=request.getParameter("openid");
			String openId;
			String access_token;
			//如果传递了opoenid，则直接使用，否则就从新获取
			if(StringUtils.isNullString(openidparam)) {

				if (StringUtils.isNullString(wxcode)) {
					return "404";
				}
				if (!StringUtils.twoStrMatch(active, "dev")) {

					JSONObject openJson = ServiceUtil.getOpenId(wxcode);
					openId = openJson.getString("openid");
					access_token = openJson.getString("access_token");
				} else {
					openId = "001";
					access_token = "";
				}
			}else {
				openId=openidparam;
				access_token="";
			}
			// 步骤1
			Wrapper<User> wrapper = new EntityWrapper<User>();
			wrapper.where("wx_opend_id ={0}", openId);
			request.setAttribute("openid", openId);
			User user = userService.selectOne(wrapper);
			if (user != null && user.getUserPhone() != null) {
				// 步骤二
				// 有这个用户，直接前往我的界面
				String headurl=HeadUrlHead+(user.getUserHeadUrl()==null?"zuofei.jpg":user.getUserHeadUrl());
				if(StringUtils.Str1ConstansStr2(user.getUserHeadUrl(), "http")) {
					headurl=user.getUserHeadUrl();
				}
				request.setAttribute("headurl", headurl);
				request.setAttribute("nickname", user.getNickName());
				request.setAttribute("message", user.getUserMessage());
				// 获取二维码数据。
				Wrapper<Car> cardw = new EntityWrapper<Car>();

				cardw.where("user_phone={0}", user.getUserPhone());
				List<Car> cards = carService.selectList(cardw);
				List<Car> rcards = new ArrayList<Car>();
				if(cards==null || cards.size()<1) {
					//直接返回，
					return "QRCode/wode";
				}
				for(Car c:cards) {
					c.setUserPhone(StringUtils.HiddenPhone(c.getUserPhone()));
					rcards.add(c);
				}
				request.setAttribute("cards", rcards);
				return "QRCode/wode";
			} else {

				if (user == null) {
					// 获取微信用户的数据
					JSONObject wxuser = ServiceUtil.getUserInfo(access_token, openId, wxcode, 0);
					if(wxuser==null) {
						FileLog.debugLog("获取微信用户为空。");
						return "404";
					}
					// 保存用户数据
					User iu = new User();
					iu.setCreateTime(new Date());
					iu.setNickName(wxuser.getString("nickname"));
					iu.setModTime(new Date());
					iu.setUserHeadUrl(wxuser.getString("headimgurl"));
					iu.setUserCity(wxuser.getString("city"));
					iu.setUserProvince(wxuser.getString("province"));
					iu.setUserSexual(wxuser.getString("sex"));
					iu.setTokenModTime(new Date());
					iu.setUserToken(access_token);
					iu.setWxOpendId(openId);
					userService.insert(iu);
				}
				// 访问绑定界面，令其进行手机号绑定
				return "QRCode/bindPhone";
			}
		} catch (Exception e) {
			FileLog.errorLog(e);
		}
		return "500";
	}

	@GetMapping("/bind")
	public String toBindPage(HttpServletRequest request) {
		if(StringUtils.twoStrMatch(active, "dev")) {

			request.setAttribute("cardNo", "777777");
			return "QRCode/bind";
		}
		/**
		 * 1.获取到二维码卡号 2.判断此卡号是否绑定过，没有则跳转到绑定页面。
		 * 
		 */
		String cardNo = request.getParameter("state");
		if (StringUtils.isNullString(cardNo)) {
			return "404";
		}
		Wrapper<Card> wra = new EntityWrapper<Card>();
		wra.where("card_no={0} and is_del={1}", cardNo,0);
		Card card = cardService.selectOne(wra);
		if (card == null) {
			 return "404";
		}else if(card.getIsEntity()!=null && card.getIsEntity()==0){
			/**
			 * ==0,表示暂时还不能使用。
			 */
			 return "404";
		}
		// 获取用户id
		String wxcode = request.getParameter("code");
		if (StringUtils.isNullString(wxcode)) {
			return "404";
		}
		String openId;
		String access_token;
		if (!StringUtils.twoStrMatch(active, "dev")) {

			JSONObject openJson = ServiceUtil.getOpenId(wxcode);
			openId = openJson.getString("openid");
			access_token = openJson.getString("access_token");
			request.setAttribute("token", access_token);
			request.setAttribute("openId", openId);
		} else {
			openId = "001";
			access_token = "";
		}
		Wrapper<User> wrapper = new EntityWrapper<User>();
		wrapper.where("wx_opend_id={0}", openId);
		request.setAttribute("openid", openId);
		User user = userService.selectOne(wrapper);
		if (user == null) {
			// 获取微信用户的数据
			JSONObject wxuser = ServiceUtil.getUserInfo(access_token, openId, wxcode, 0);
			if(wxuser==null) {
				FileLog.debugLog("错误的用户。");
				return "404";
			}
			// 保存用户数据
			User iu = new User();
			iu.setCreateTime(new Date());
			iu.setNickName(wxuser.getString("nickname"));
			iu.setModTime(new Date());
			iu.setUserHeadUrl(wxuser.getString("headimgurl"));
			iu.setUserCity(wxuser.getString("city"));
			iu.setUserProvince(wxuser.getString("province"));
			iu.setUserSexual(wxuser.getString("sex"));
			iu.setTokenModTime(new Date());
			iu.setUserToken(access_token);
			iu.setWxOpendId(openId);
			userService.insert(iu);
		}
		request.setAttribute("cardNo", cardNo);
		FileLog.debugLog("访问绑定页面");
		return "QRCode/bind";
	}

	@GetMapping("/index")
	public String toIndexPage(HttpServletRequest request, String cardNo) {
		try {
//			request.setAttribute("headurl", "https://qr.api.cli.im/qr?data=http%253A%252F%252Fwww.banxue.fun%252Fqrcode%252Findex%253FcardNo%253D100014&level=H&transparent=false&bgcolor=%23ffffff&forecolor=%23000000&blockpixel=12&marginblock=1&logourl=&size=260&kid=cliim&key=127878ca0533d96402bc42866d8c4241");
//			request.setAttribute("message", "测试测试");
//			request.setAttribute("nickname", "纷纷");
//
//			return "QRCode/index";
			/**
			 * 1.获取到二维码卡号 2.判断此卡号是否绑定过，没有则跳转到绑定页面。
			 * 
			 */
			if (StringUtils.isNullString(cardNo)) {
				return "404";
			}
			Wrapper<Card> wra = new EntityWrapper<Card>();
			wra.where("card_no={0} and is_del={1}", cardNo,0);
			Card card = cardService.selectOne(wra);
			if (card == null) {
				return "404";
			}
			request.setAttribute("cardNo", cardNo);
			if (StringUtils.isNullString(card.getUserPhone())) {
				// 到绑定页面
				FileLog.debugLog("未绑定用户，跳转至绑定页面。");
				return "QRCode/jump";
			}
			/**
			 * 逻辑分析： 1.如果此卡片已经绑定了，则直接返回该卡片对应的用户信息
			 */
			Wrapper<User> wru = new EntityWrapper<User>();
//			wru.addFilter("user_phone", card.getUserPhone());
			wru.where("user_phone={0}", card.getUserPhone());
			User u = userService.selectOne(wru);
			if (u == null) {
				// 到绑定页面
				FileLog.debugLog("未找到用户，跳转至绑定页面。");
				return "QRCode/jump";
			}
			String headurl=HeadUrlHead+(u.getUserHeadUrl()==null?"zuofei.jpg":u.getUserHeadUrl());
			if(StringUtils.Str1ConstansStr2(u.getUserHeadUrl(), "http")) {
				headurl=u.getUserHeadUrl();
			}
			request.setAttribute("headurl", headurl);
			request.setAttribute("message", u.getUserMessage());
			request.setAttribute("nickname", u.getNickName());

			return "QRCode/index";
		} catch (Exception e) {
			FileLog.errorLog(e);
		}
		return "error";
	}

	@GetMapping("/mod")
	public String toModPage(HttpServletRequest request, String openid) {
		if (StringUtils.isNullString(openid)) {
			return "404";
		} else {
			request.setAttribute("openid", openid);
		}
		FileLog.debugLog("访问修改个人信息页面");
		return "QRCode/mod";
	}
	
	@GetMapping("/bindPhone")
	public String bindPhone(HttpServletRequest request, String openid) {
		if (StringUtils.isNullString(openid)) {
			return "404";
		} else {
			request.setAttribute("openid", openid);
		}
		FileLog.debugLog("访问修改个人信息页面");
		return "QRCode/bindPhone";
	}

	@GetMapping("/modbind")
	public String toModBindPage(HttpServletRequest request, String openid,String cardNo) {
		if (StringUtils.isNullString(openid,cardNo)) {
			return "404";
		} else {
			request.setAttribute("openid", openid);
			request.setAttribute("cardNo", cardNo);
		}
		FileLog.debugLog("访问修改绑定信息页面");
		return "QRCode/modBind";
	}

	@GetMapping("/blackbar")
	public String blackbar(HttpServletRequest request) {
		
		return "blackBar";
	}

}
