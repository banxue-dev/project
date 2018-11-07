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
/*
@RunWith(SpringRunner.class)
@SpringBootTest*/
public class BanxueInterfaceApplicationTests {

	String accID="8a216da865ae11f80165b88ae40b0485";
	String authToken="c5f4ad986c0d453a9be93724b803ba3a";
	@Test
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

}
