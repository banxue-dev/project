package com.banxue.timer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.banxue.utils.StringUtils;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.wx.WxConstants;
import com.banxue.utils.wx.WxUtils;

/**
作者：fengchase
时间：2018年10月26日
文件：TimerTask.java
项目：banxue-frontend
*/
@Component
public class TimerTask {
	@Value("${spring.profiles.active}")
	private String ispro;
	
	@Scheduled(cron = "0 0 0/2 * * ? ")
//	@Scheduled(cron = "*/5 * * * * ?")
    public void scheduled(){
        FileLog.debugLog("执行任务定时。");
        if(StringUtils.twoStrMatch("pro", ispro)) {
	        JSONObject obj=WxUtils.geWxtAccessToken();
	        String token=obj.getString("access_token");
	        WxConstants.WxToken=token;
        }
    }
}

