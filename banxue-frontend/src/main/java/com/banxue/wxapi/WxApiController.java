package com.banxue.wxapi;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banxue.utils.wx.StrXmlToMap;
import com.banxue.utils.wx.WXBizMsgCrypt;
import com.banxue.utils.wx.WxMessageUtil;
import com.banxue.utils.wx.WxUtils;

/**
 * 作者：fengchase 时间：2018年9月29日 文件：WxApiController.java 项目：banxue-frontend
 */
@Controller
@RequestMapping("/wxapi")
public class WxApiController {

	@GetMapping("/wxService")
	@ResponseBody
	public String wxServiceGet(HttpServletRequest request) {
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

	@PostMapping("/wxService")
	@ResponseBody
	public String wxServicePost(HttpServletRequest request) {
		try {
			WXBizMsgCrypt wx = WXBizMsgCrypt.getWxCrypt();
			// 从request中取得输入流
			InputStream inputStream = request.getInputStream();
			// 转为String
			String xml = StrXmlToMap.ISXmlToString(inputStream);
			// 解密
			String res = wx.decryptMsg(xml);
			return WxMessageUtil.responseMessage(res);
			// return wx.encryptMsg("已收到你的消息，马上前往支援。", DateUtils.format(new Date(),
			// DateUtils.DATE_TIME_STAMP), WxUtils.getRandomStr());
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
		return null;
	}
}
