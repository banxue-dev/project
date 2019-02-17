package com.banxue.website.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
作者：fengchase
时间：2019年1月24日
文件：WebSiteController.java
项目：banxue-frontend
*/@Controller
@RequestMapping("/")
public class WebSiteController {
	
	
	@RequestMapping("/")
	public String toWebIndex() {
		return "website/index";
	}
	@RequestMapping("/case")
	public String toWebCase() {
		return "website/case";
	}
	@RequestMapping("/index")
	public String toWebIndex2() {
		return "website/index";
	}
	@RequestMapping("/about")
	public String toWebAbout() {
		return "website/about";
	}
}

