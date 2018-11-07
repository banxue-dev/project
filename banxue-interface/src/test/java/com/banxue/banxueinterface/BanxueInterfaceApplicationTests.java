package com.banxue.banxueinterface;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.banxue.utils.Base64Utils;
import com.banxue.utils.DateUtils;
import com.banxue.utils.HttpUtils;
import com.banxue.utils.MD5Utils;
import com.banxue.utils.ppc.HWConfig;
import com.banxue.utils.ppc.service.IAXInterfaceDemo;
import com.banxue.utils.ppc.service.impl.AXInterfaceDemoImpl;
/*
@RunWith(SpringRunner.class)
@SpringBootTest*/
public class BanxueInterfaceApplicationTests {

	String accID="8a216da865ae11f80165b88ae40b0485";
	String authToken="c5f4ad986c0d453a9be93724b803ba3a";
	public void contextLoads() {
		
		Date date=new Date();
		String Authorization="8a216da865ae11f80165b88ae40b0485:"+DateUtils.format(date,DateUtils.DATE_TIME_STAMP);
		String sign=accID+authToken+DateUtils.format(date,DateUtils.DATE_TIME_STAMP);
		String url="https://apppro.cloopen.com:8883/2013-12-26/Accounts/8a216da865ae11f80165b88ae40b0485/nme/axb/028/providenumber?sig="+MD5Utils.MD5Encode(sign, "utf-8");
		JSONObject param=new JSONObject();
		param.put("appId", "8a216da8669b2ef70166a021f17903ea");
		param.put("aNumber", "13524954089");
		param.put("bNumber", "17360133424");
		param.put("mappingDuration", "120");
//		param.put("Sig", MD5Utils.MD5Encode(sign, "utf-8"));
		try {
			String res=HttpUtils.callpost(url, param,Base64Utils.encode(Authorization));
			System.out.println(res);
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void ppctest() {
		IAXInterfaceDemo ax = new AXInterfaceDemoImpl(HWConfig.OMPAPPKEY, HWConfig.OMPAPPSECRET, HWConfig.OMPDOMAINNAME);

        // 第一步: 号码绑定,即调用AX模式绑定接口
//         ax.axBindNumber("+8617360133424", "+8617138398842", "0");

        // 当用户发起通话时,隐私保护通话平台会将呼叫事件推送到商户应用,参考: HostingVoiceEventDemoImpl
        // 当用户使用短信功能,隐私保护通话平台将短信事件推送到商户应用,参考: HostingVoiceEventDemoImpl

        // 第二步: 用户通过隐私号码发起呼叫后,商户可随时终止一路呼叫,即调用终止呼叫接口
        // ax.axStopCall("1200_366_0_20161228102743@callenabler.home1.com");

        // 第三步: 用户通话结束,若设置录音,则商户可以获取录音文件下载地址,即调用获取录音文件下载地址接口
        // ax.axGetRecordDownloadLink("ostr.huawei.com", "1200_366_0_20161228102743.wav");

        // 第四步: 根据业务需求,可更改绑定关系,即调用AX模式绑定信息修改接口
        // ax.axModifyNumber("efw89efwf7fea324252", null, null, true);

        // 第五步: 根据业务需求,设置临时被叫,即调用AX模式设置临时被叫接口
        // ax.axSetCalleeNumber(null, "+8618612345678", "+8617010000001", "+8618612345679");

        // 第六步: 隐私号码循环使用,商户可将绑定关系解绑,即调用AX模式解绑接口
//         ax.axUnbindNumber(null, "+8613524954089", "+8617138398842");

        // 第七步: 商户可查询已订购的隐私号码的绑定信息,即调用AX模式绑定信息查询接口
        ax.axQueryBindRelation("1541577150153c5b100cfb53240d2917401c189b84164", null);
	}

}
