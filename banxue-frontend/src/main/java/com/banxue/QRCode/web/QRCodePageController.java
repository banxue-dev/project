package com.banxue.QRCode.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.banxue.QRCode.entity.Card;
import com.banxue.QRCode.entity.User;
import com.banxue.QRCode.service.ICardService;
import com.banxue.QRCode.service.IUserService;
import com.banxue.utils.StringUtils;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.pay.wex.ServiceUtil;
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

	@GetMapping("/my")
	public String toWodePage(HttpServletRequest request) {
		try {
			/**
			 * 逻辑 1.获取到code后获取用户的openid，然后去后台查询此openid是否绑定了手机号。
			 * 2.如果没有绑定或者后台没有保存过这条openId，直接跳转到绑定页面 。
			 * 3.如果后台没有保存过这条，则将此条用户入库。
			 * 4.将此openid传入前台用于绑定。
			 */
			// 获取用户id
			String wxcode = request.getParameter("code");
			if (StringUtils.isNullString(wxcode)) {
				return "404";
			}
			JSONObject openJson = ServiceUtil.getOpenId(wxcode);
			String openId = openJson.getString("openid");
			String access_token = openJson.getString("access_token");
			request.setAttribute("token", access_token);
			request.setAttribute("openId", openId);
			// 步骤1
			Wrapper<User> wrapper = new EntityWrapper<User>();
			wrapper.addFilter("wx_opend_id", openId);
			User user = userService.selectOne(wrapper);
			if (user != null && user.getUserPhone() != null) {
				// 步骤二
				// 有这个用户，直接前往我的界面
				request.setAttribute("headurl", user.getUserHeadUrl());
				request.setAttribute("nickname", user.getNickName());
				request.setAttribute("message", user.getUserMessage());
				return "QRCode/wode";
			} else {

				request.setAttribute("openId", openId);
				if (user == null) {
					// 获取微信用户的数据
					JSONObject wxuser = ServiceUtil.getUserInfo(access_token, openId, wxcode, 0);
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
	public String toBindPage() {
		FileLog.debugLog("访问绑定页面");
		return "QRCode/bind";
	}

	@GetMapping("/index")
	public String toIndexPage(HttpServletRequest request) {
		try {
			/**
			 * 1.获取到二维码卡号
			 * 2.判断此卡号是否绑定过，没有则跳转到绑定页面。
			 * 
			 */
			String cardNo=request.getParameter("carNo");
			Wrapper<Card> wra=new EntityWrapper<Card>();
			Card card=cardService.selectOne(wra);
			if(card==null) {
				//到绑定页面
				request.setAttribute("cardNo", cardNo);
				return "QRCode/bind";
			}
		}catch(Exception e) {
			FileLog.errorLog(e);
		}
		return "QRCode/index";
	}

	@GetMapping("/mod")
	public String toModPage() {
		FileLog.debugLog("访问修改个人信息页面");
		return "QRCode/mod";
	}

	@GetMapping("/modbind")
	public String toModBindPage() {
		FileLog.debugLog("访问修改绑定信息页面");
		return "QRCode/modbind";
	}

}
