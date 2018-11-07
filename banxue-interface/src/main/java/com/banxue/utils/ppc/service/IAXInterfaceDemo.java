package com.banxue.utils.ppc.service;

/**
 * AX模式接口
 */
public interface IAXInterfaceDemo {
    /**
     * Set the X number to the user's privacy number | 隐私号码AX绑定
     * 
     * @param origNum          用户号码
     * @param privateNum       隐私号码
     * @param calleeNumDisplay 是否显示用户号码
     */
    void axBindNumber(String origNum, String privateNum, String calleeNumDisplay);

    /**
     * Modify sms function of the user's privacy number | 隐私号码AX绑定信息修改
     * 
     * @param subscriptionId 绑定关系ID
     * @param origNum        用户号码
     * @param privateNum     隐私号码
     * @param privateSms     是否支持短信
     *                       subscriptionId和(origNum,privateNum)二选一即可,当都传入时,优先选用subscriptionId
     */
    void axModifyNumber(String subscriptionId, String origNum, String privateNum, boolean privateSms);

    /**
     * Unbind the privacy number from number a | 隐私号码AX解绑
     * 
     * @param subscriptionId 绑定关系ID
     * @param origNum        用户号码
     * @param privateNum     隐私号码
     *                       subscriptionId和(origNum,privateNum)二选一即可,当都传入时,优先选用subscriptionId
     */
    void axUnbindNumber(String subscriptionId, String origNum, String privateNum);

    /**
     * Query the privacy binding numbers on the X number | 查询AX绑定信息
     * 
     * @param subscriptionId 绑定关系ID
     * @param origNum        用户号码
     *                       subscriptionId和origNum二选一即可,当都传入时,优先选用subscriptionId
     */
    void axQueryBindRelation(String subscriptionId, String origNum);

    /**
     * Set the callee number to the ax bind relation | 设置AX临时被叫
     * 
     * @param subscriptionId 绑定关系ID
     * @param origNum        原始号码
     * @param privateNum     隐私号码
     * @param calleeNum      被叫号码
     *                       subscriptionId和(origNum,privateNum)二选一即可,当都传入时,优先选用subscriptionId
     */
    void axSetCalleeNumber(String subscriptionId, String origNum, String privateNum, String calleeNum);

    /**
     * Get download link of the record file created in call | 获取录音文件下载地址
     * 
     * @param recordDomain 录音文件存储的服务器域名
     * @param fileName     录音文件名
     */
    void axGetRecordDownloadLink(String recordDomain, String fileName);

    /**
     * Stop the call on the X number assigned by sessionid | 终止呼叫
     * 
     * @param sessionid 呼叫会话ID 通过“呼叫事件和话单通知接口”获取
     */
    void axStopCall(String sessionid);
}