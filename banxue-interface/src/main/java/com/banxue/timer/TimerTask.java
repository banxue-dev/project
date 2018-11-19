package com.banxue.timer;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.banxue.privatenum.entity.PrivateNum;
import com.banxue.privatenum.service.IPrivateNumService;
import com.banxue.utils.ResultEntity;
import com.banxue.utils.StringUtils;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.ppc.HWConfig;
import com.banxue.utils.ppc.service.IAXInterfaceDemo;
import com.banxue.utils.ppc.service.impl.AXInterfaceDemoImpl;
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
	@Autowired
	private IPrivateNumService iPrivateNumService;
	IAXInterfaceDemo ax = new AXInterfaceDemoImpl(HWConfig.OMPAPPKEY, HWConfig.OMPAPPSECRET, HWConfig.OMPDOMAINNAME);
//	@Scheduled(cron = "*/5 * * * * ?")
	@Scheduled(cron = "0 0 0/2 * * ? ")
    public void scheduledByWxToken(){
        if(StringUtils.twoStrMatch("pro", ispro)) {
        	updateWxToken();
        }
    }
	@Scheduled(cron = "0 0/2 * * * ? ")
	public void scheduledByPrivateNum(){
		if(StringUtils.twoStrMatch("pro", ispro)) {
			updatePrivateNumer();
		}
	}
	/**
	 * 更新wxtoken
	 * 
	 * 2018年11月7日
	 * 作者：fengchase
	 */
	public static void updateWxToken() {
		JSONObject obj=WxUtils.geWxtAccessToken();
        String token=obj.getString("access_token");
        WxConstants.WxToken=token;
        FileLog.debugLog("微信token已更新。"+token);
	}
	/**
	 * 释放隐私号码。
	 * 
	 * 2018年11月7日
	 * 作者：fengchase
	 */
	public  void updatePrivateNumer() {
		try {
			List<PrivateNum> pnlst= iPrivateNumService.getListByState(1);
			
			for(PrivateNum tn:pnlst) {
				Long now=new Date().getTime();
				Long aft=Long.valueOf(tn.getModTimeLong());
				Long mat=(long) (2*60*1000);
				if(now-aft>mat) {
					//已超时，自动释放
					//解绑
					ResultEntity re= ax.axUnbindNumber(tn.getBindGxId(), null, null);
					if(re.isState()) {
						//成功,修改数据库状态为空闲。
						tn.setState(0);
						iPrivateNumService.updateById(tn);
					}else {
						FileLog.debugLog("解绑失败"+re.getDesc());
					}

			        FileLog.debugLog("任务已执行。释放号码："+tn.getPrivateNumber());
				}
			}

		}catch(Exception e) {
			FileLog.errorLog("定时任务出错："+e);
		}
	}
}

