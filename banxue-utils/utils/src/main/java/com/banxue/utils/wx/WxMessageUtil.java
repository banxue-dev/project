package com.banxue.utils.wx;

import java.util.Map;

import org.dom4j.DocumentException;

import com.banxue.utils.StringUtils;
import com.banxue.utils.log.FileLog;

/**
 * 作者：fengchase 时间：2018年7月13日 文件：WxMessageUtil.java 项目：banxue-backend 微信消息回复的类
 */
public class WxMessageUtil {

	public static String responseMessage(String requestStr) throws DocumentException {
		// 转为map
		Map<String, String> map = StrXmlToMap.strXmltoMap(requestStr);
		String fromUserName = map.get("FromUserName");// 公众号
		String toUserName = map.get("ToUserName");// 粉丝号
		String msgType = map.get("MsgType");// 发送的消息类型[比如 文字,图片,语音。。。]
		String content = map.get("Content");// 发送的消息内容
		System.out.println("类型：" + msgType + "消息：" + content);
		String responseMsg = "收到，over。";
		if (msgType.contains("text")) {

			switch (content) {
			case "主页":
				responseMsg = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx24afd57c69514a36&redirect_uri=http%3A%2F%2Fwww.banxue.fun%2Fqrcode%2Fmy&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";
				break;
			case "二货":
				responseMsg = "你才是二货。";
				break;
			case "测试":
				responseMsg = "不需要测试。";
				break;
			default:
				responseMsg = "已收到你的消息，请等待回复。";
				break;

			}
		} else if (msgType.contains(WxConstants.MESSAGE_EVENT)) {

			String eventType = map.get("Event");
			FileLog.debugLog("事件类型：" + eventType);
			// 事件类型
			// 订阅
			if (eventType.equals(WxConstants.MESSAGE_EVENT_SUBSCRIBE)) {
				responseMsg = "感谢您的支持。";
			}else if (eventType.equals(WxConstants.MESSAGE_EVENT_UNSUBSCRIBE)) {
				// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
			}else if (eventType.equals(WxConstants.MESSAGE_EVENT_CLICK)) {
				String eventKey = map.get("EventKey");
				if (StringUtils.twoStrMatch(eventKey, "sw_hz")) {
					/**
					 * 返回图片消息
					 */
					return StrXmlToMap.initImages(toUserName, fromUserName,
							"nq3iKbKV8qkCB5kYyvzb-qsKmFL2CFGbEwPpRT4x1FI");
				}

			}
		}
		return StrXmlToMap.initText(toUserName, fromUserName, responseMsg);
	}
}
