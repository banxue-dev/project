package com.banxue.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.banxue.utils.R;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.wx.WxConstants;
import com.banxue.utils.wx.WxUtils;

/**
作者：fengchase
时间：2019年2月27日
文件：WxPayController.java
项目：banxue-frontend
*/

@Controller
@RequestMapping("/qie")
public class TestController {
	@RequestMapping("/getTicket")
	@ResponseBody
	public R orderPage(HttpServletRequest request,String url) {
		try {
			JSONObject at=WxUtils.geWxtAccessToken();
			
			FileLog.debugLog("url:"+url);
			String ticket=WxUtils.getJsapiTicket(at.getString("access_token"));
			FileLog.debugLog("ticket:"+ticket);
			return R.okdata(WxUtils.sign(ticket, url));
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
		return null;
	}
	@GetMapping("/toqieclear")
	public String toqieclear(HttpServletRequest request,String url) {
		try {
			return "test/qieclear";
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
		return null;
	}
	
	
	

}

