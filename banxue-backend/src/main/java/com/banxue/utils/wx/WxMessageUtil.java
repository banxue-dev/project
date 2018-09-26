package com.banxue.utils.wx;

import java.util.Map;

import org.dom4j.DocumentException;

/**
作者：fengchase
时间：2018年7月13日
文件：WxMessageUtil.java
项目：banxue-backend
微信消息回复的类
*/
public class WxMessageUtil {

	public static String responseMessage(String requestStr) throws DocumentException {
		//转为map
        Map<String,String> map=StrXmlToMap.strXmltoMap(requestStr);
        String fromUserName = map.get("FromUserName");//公众号
        String toUserName = map.get("ToUserName");//粉丝号
        String msgType = map.get("MsgType");//发送的消息类型[比如 文字,图片,语音。。。]
        String content = map.get("Content");//发送的消息内容
        System.out.println("类型："+msgType+"消息："+content);
        String responseMsg="收到，over。";
        if(msgType.contains("text")) {
        	
        	switch (content) {
        	case "绑定":
        		responseMsg="http://app.cdqckj.com/wx/ble/bindPage";
        		break;
        	case "二货":
        		responseMsg="你才是二货。";
        		break;
        	case "测试":
        		responseMsg="不需要测试。";
        		break;
        	default:
        		responseMsg="已收到你的消息，马上前往支援。";
        		break;
        		
        	}
        }
        return StrXmlToMap.initText(toUserName, fromUserName, responseMsg); 
	}
}

