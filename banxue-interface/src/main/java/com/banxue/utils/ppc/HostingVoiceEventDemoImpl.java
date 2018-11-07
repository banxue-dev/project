package com.banxue.utils.ppc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 呼叫事件与话单通知/短信通知 客户平台收到隐私保护通话平台的呼叫事件或短信事件的接口通知
 */
public class HostingVoiceEventDemoImpl {
    /**
     * 短信通知
     * 
     * @param jsonBody
     * @breif 详细内容以接口文档为准
     */
    public void onSmsEvent(String jsonBody) {
        // 封装JOSN请求
        JSONObject json = JSON.parseObject(jsonBody);
        String appKey = json.getString("appKey"); // 商户应用的AppKey

        JSONObject smsEvent = json.getJSONObject("smsEvent"); // 短信状态事件
        String smsIdentifier = smsEvent.getString("smsIdentifier"); // 短信唯一标识
        String calling = smsEvent.getString("calling"); // 真实发送方号码
        String called = smsEvent.getString("called"); // 真实接收方号码
        String virtualNumber = smsEvent.getString("virtualNumber"); // 隐私号码(X号码)
        String event = smsEvent.getString("event"); // 短信状态事件
        String timeStamp = smsEvent.getString("timeStamp"); // 短信事件发生的系统时间戳
        String notificationMode = smsEvent.getString("notificationMode"); // 通知模式

        // 如果是Block模式,则要按接口文档进行回复响应
        if ("Block".equalsIgnoreCase(notificationMode)) {
            JSONObject resp = new JSONObject();
            JSONArray actions = new JSONArray();

            JSONObject action = new JSONObject();
            action.put("operation", "vNumberRoute"); // 操作类型
            actions.add(action);

            resp.put("actions", actions);
        }

        String subscriptionId = smsEvent.getString("subscriptionId"); // 绑定关系ID

        JSONObject extInfo = smsEvent.getJSONObject("extInfo"); // 拓展信息
        JSONObject extParas = extInfo.getJSONObject("extParas"); // 预留参数,当前版本无需关注

    }

    /**
     * 呼叫事件
     * 
     * @param jsonBody
     * @breif 详细内容以接口文档为准
     */
    public void onCallEvent(String jsonBody) {
        // 封装JOSN请求
        JSONObject json = JSON.parseObject(jsonBody);
        String eventType = json.getString("eventType"); // 通知的事件类型

        JSONObject statusInfo = json.getJSONObject("statusInfo"); // 呼叫状态事件的信息
        String timestamp = statusInfo.getString("timestamp"); // 呼叫事件发生时隐私保护通话平台的UNIX时间戳
        String callUserData = statusInfo.getString("userData"); // 用户附属信息
        String callSessionId = statusInfo.getString("sessionId"); // 通话链路的标识ID
        String caller = statusInfo.getString("caller"); // 主叫号码
        String called = statusInfo.getString("called"); // 被叫号码
        Integer stateCode = statusInfo.getInteger("stateCode"); // 通话挂机的原因值
        String stateDesc = statusInfo.getString("stateDesc"); // 通话挂机的原因值的描述
        String origCalleeNum = statusInfo.getString("origCalleeNum"); // 呼叫的原始被叫号码,即隐私号码(X)
        String displayCallerNum = statusInfo.getString("displayCallerNum"); // 主显号码
        String digitInfo = statusInfo.getString("digitInfo"); // 预留字段,用于在放音收号场景中携带收号结果(即用户输入的数字)
        String callSubscriptionId = statusInfo.getString("subscriptionId"); // 绑定关系ID
        String callNotifyMode = statusInfo.getString("notifyMode"); // 通知模式,仅X模式场景携带

        if ("Block".equalsIgnoreCase(callNotifyMode)) {
            JSONObject resp = new JSONObject();

            resp.put("operation", "connect"); // 用于指示平台的呼叫操作
            // resp.put("operation", "close");

            JSONArray connectInfoArr = new JSONArray();
            JSONObject connectInfo = new JSONObject();
            connectInfo.put("displayCalleeNum", "+8613400000001"); // 被叫端的来显号码
            connectInfo.put("calleeNum", "+8613400000001"); // 真实被叫号码
            connectInfo.put("maxDuration", 60); // 单次通话最长时间
            connectInfo.put("waitVoice", "waitVoice001.wav"); // 个性化通话前等待音
            connectInfo.put("recordFlag", "true"); // 是否通话录音
            connectInfo.put("recordHintTone", "recordHintTone001.wav"); // 录音提示音
            connectInfo.put("lastMinVoice", "lastMinVoice001.wav"); // 通话最后一分钟提示音
            connectInfo.put("userData", "userflag001"); // 用户自定义数据
            connectInfoArr.add(connectInfo);
            resp.put("connectInfo", connectInfoArr); // 指示平台接续被叫通话的参数列表

            JSONArray closeInfoArr = new JSONArray();
            JSONObject closeInfo = new JSONObject();
            closeInfo.put("closeHintTone", "closeHintTone001.wav"); // 挂机提示音
            closeInfo.put("userData", "userflag001"); // 用户自定义数据
            closeInfoArr.add(closeInfo);
            resp.put("closeInfo", closeInfoArr); // 指示平台结束会话的参数列表
        }
    }

    /**
     * 话单通知
     * 
     * @param jsonBody
     * @breif 详细内容以接口文档为准
     */
    public void onFeeEvent(String jsonBody) {
        // 封装JOSN请求
        JSONObject json = JSON.parseObject(jsonBody);
        String eventType = json.getString("eventType"); // 通知的事件类型

        JSONObject feeLst = json.getJSONObject("feeLst"); // 呼叫话单事件的信息
        Integer direction = feeLst.getInteger("direction"); // 通话的呼叫方向
        String spId = feeLst.getString("spId"); // 客户的云服务账号
        String appKey = feeLst.getString("appKey"); // 商户应用的AppKey
        String icid = feeLst.getString("icid"); // 呼叫记录的唯一标识
        String feeSessionId = feeLst.getString("sessionId"); // 通话链路的唯一标识
        String callerNum = feeLst.getString("callerNum"); // 主叫号码
        String calleeNum = feeLst.getString("calleeNum"); // 被叫号码
        String fwdDisplayNum = feeLst.getString("fwdDisplayNum"); // 转接呼叫时的显示号码
        String fwdDstNum = feeLst.getString("fwdDstNum"); // 转接呼叫时的转接号码
        String callInTime = feeLst.getString("callInTime"); // 呼入的开始时间
        String fwdStartTime = feeLst.getString("fwdStartTime"); // 转接呼叫操作的开始时间
        String fwdAlertingTime = feeLst.getString("fwdAlertingTime"); // 转接呼叫操作后的振铃时间
        String fwdAnswerTime = feeLst.getString("fwdAnswerTime"); // 转接呼叫操作后的应答时间
        String callEndTime = feeLst.getString("callEndTime"); // 呼叫结束时间
        Integer fwdUnaswRsn = feeLst.getInteger("fwdUnaswRsn"); // 转接呼叫操作失败的Q850原因值
        String failTime = feeLst.getString("failTime"); // 呼入,呼出的失败时间
        Integer ulFailReason = feeLst.getInteger("ulFailReason"); // 通话失败的拆线点
        Integer sipStatusCode = feeLst.getInteger("sipStatusCode"); // 呼入,呼出的失败SIP状态码
        Integer recordFlag = feeLst.getInteger("recordFlag"); // 录音标识
        String recordStartTime = feeLst.getString("recordStartTime"); // 录音开始时间
        String recordObjectName = feeLst.getString("recordObjectName"); // 录音文件名
        String recordBucketName = feeLst.getString("recordBucketName"); // 录音文件所在的目录名
        String recordDomain = feeLst.getString("recordDomain"); // 存放录音文件的域名
        String recordPushURL = feeLst.getString("recordPushURL"); // 隐私保护通话平台向录音存储服务器推送录音文件的URL
        String serviceType = feeLst.getString("serviceType"); // 携带呼叫的业务类型信息
        String hostName = feeLst.getString("hostName"); // 话单生成的服务器设备对应的主机名
        String feeUserData = feeLst.getString("userData"); // 用户附属信息
        String feeSubscriptionId = feeLst.getString("subscriptionId"); // 绑定关系ID
        String feeNotifyMode = feeLst.getString("notifyMode"); // 通知模式,仅在X模式场景携带

        if ("Block".equalsIgnoreCase(feeNotifyMode)) {
            // Block模式下,参考接口文档回复响应消息,指示下一步操作
        }
    }
}