package com.banxue.QRCode.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		return "QRCode/wode";
	}

}

