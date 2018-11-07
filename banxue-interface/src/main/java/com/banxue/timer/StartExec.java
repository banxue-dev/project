package com.banxue.timer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.banxue.utils.StringUtils;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.wx.WxConstants;
import com.banxue.utils.wx.WxUtils;

/**
作者：fengchase
时间：2018年10月26日
文件：StartExec.java
项目：banxue-interface
*/
@Component
public class StartExec implements CommandLineRunner{

	@Value("${spring.profiles.active}")
	private String ispro;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO 此处为方法主题
		 FileLog.debugLog("开机启动任务。");
	        if(StringUtils.twoStrMatch("pro", ispro)) {
	        	TimerTask.updateWxToken();
	        }
	}

}

