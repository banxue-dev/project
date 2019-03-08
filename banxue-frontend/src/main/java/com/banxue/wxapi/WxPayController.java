package com.banxue.wxapi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banxue.utils.wx.WxUtils;

/**
作者：fengchase
时间：2019年2月27日
文件：WxPayController.java
项目：banxue-frontend
*/

@Controller
@RequestMapping("/wxpay")
public class WxPayController {
	@PostMapping("/orderPage")
	@ResponseBody
	public String orderPage(HttpServletRequest request) {
		try {
			String msgSignature = request.getParameter("signature");
			String msgTimestamp = request.getParameter("timestamp");
			String msgNonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");
			// 这里的 WXPublicConstants.TOKEN 填写你自己设置的Token就可以了
			if (WxUtils.verifyUrl(msgSignature, msgTimestamp, msgNonce)) {
				return echostr;
			}
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
		return null;
	}
	
	

}

