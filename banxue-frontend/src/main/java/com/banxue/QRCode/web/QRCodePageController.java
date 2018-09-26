package com.banxue.QRCode.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banxue.utils.log.FileLog;

/**
作者：fengchase
时间：2018年9月26日
文件：QRCodePageController.java
项目：banxue-frontend
*/
@Controller
@RequestMapping("/qrcode")
public class QRCodePageController {
	
	@GetMapping("/my")
	public String toWodePage() {
		FileLog.debugLog("访问我的页面");
		return "QRCode/wode";
	}
	@GetMapping("/bind")
	public String toBindPage() {
		FileLog.debugLog("访问绑定页面");
		return "QRCode/bind";
	}
	@GetMapping("/index")
	public String toIndexPage() {
		FileLog.debugLog("访问主页");
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

