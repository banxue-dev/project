package com.banxue.utils.pay;

import java.io.UnsupportedEncodingException;
import java.util.SortedMap;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.banxue.utils.DateUtils;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.pay.ali.AlipayConfig;
import com.banxue.utils.pay.pojo.PayOrderDO;
import com.banxue.utils.pay.pojo.RequestHandler;
import com.banxue.utils.pay.wex.ServiceUtil;
import com.banxue.utils.pay.wex.WeixinUtils;
import com.banxue.utils.pay.wex.WxPayConfig;
import com.banxue.utils.wx.WxUtils;

/**
 * 作者：fengchase 时间：2018年7月18日 文件：PayUtils.java 项目：banxue-backend
 */
public class PayUtils {

	public static String getAliPayOrderInfos(PayOrderDO order) throws AlipayApiException {
		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayConfig.APPID, // appid
				AlipayConfig.RSA_PRIVATE_KEY, // 私钥
				"json", // 格式，仅支持json
				AlipayConfig.CHARSET, // 请求编码格式
				AlipayConfig.ALIPAY_PUBLIC_KEY, // 应用公钥
				"RSA2");// 签名算法类型，支持RSA2和RSA，推荐使用的是RSA2
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(order.getOrderRemark());
		model.setSubject("标题");
		model.setOutTradeNo(order.getOrderNo());
		model.setTimeoutExpress("30m");// 一般用不到这个
		model.setTotalAmount(order.getPayPrice());// 这个嘛就是钱喽
		model.setProductCode("QUICK_MSECURITY_PAY");// 商家和支付宝签约的产品码，为固定值
		request.setBizModel(model);
		request.setNotifyUrl(AlipayConfig.notify_url);// 外网异步回调地址，是需要外网能够访问到的
		// 这里和普通的接口调用不同，使用的是sdkExecute
		AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
		response.getCode();
		return response.getBody();

	}

	/**
	 * 微信客户端授权成功后根据redirect_uri参数调整到pay接口，去准备支付前信息接口 用于前端调起支付
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String getWexPayOrderInfos(PayOrderDO order) {
		try {
			/**
			 * 第一步：用户同意授权，根据参数，获取code
			 * 授权成功后返回的授权码，参考：http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html#.E7.AC.AC.E4.B8.80.E6.AD.A5.EF.BC.9A.E7.94.A8.E6.88.B7.E5.90.8C.E6.84.8F.E6.8E.88.E6.9D.83.EF.BC.8C.E8.8E.B7.E5.8F.96code
			 */
//			String code = order.getWxCode();
//			String state = order.getWxState();
//
//			// state可以为任何含义，根据你前端需求，这里暂时叫商品id
//			// 授权码、商品id
//			System.out.println("code=" + code + ",state=" + state);

			/**
			 * 第二步：通过code换取网页授权access_token
			 * 根据授权码code获取access_token，参考：http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html#.E7.AC.AC.E4.BA.8C.E6.AD.A5.EF.BC.9A.E9.80.9A.E8.BF.87code.E6.8D.A2.E5.8F.96.E7.BD.91.E9.A1.B5.E6.8E.88.E6.9D.83access_token
			 */
			// 下面就到了获取openid,这个代表用户id.
			// 获取openID
//			JSONObject userObj=ServiceUtil.getOpenId(code);
//			String openid = userObj.getString("openid");
			String openid = order.getOpenid();
			// 生成随机字符串
			String noncestr = WxUtils.getRandomStr();
			// 生成1970年到现在的秒数.
			String timestamp = DateUtils.getNowTimestammp();
			// 订单号，自定义生成规则，
			String out_trade_no = order.getOrderNo();
			// 订单金额，应该是根据state（商品id）从数据库中查询出来
			String order_price = String.valueOf(order.getPayPrice());
			// 商品描述，应该是根据state（商品id）从数据库中查询出来
			String body = order.getOrderRemark();

			/**
			 * 第三步：统一下单接口
			 * 需要第二步生成的openid，参考：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
			 */
			RequestHandler reqHandler = new RequestHandler();
			// 初始化 RequestHandler 类可以在微信的文档中找到.还有相关工具类
			reqHandler.init();
			// 执行统一下单接口 获得预支付id，一下是必填参数

			// 微信分配的公众账号ID（企业号corpid即为此appId）
			reqHandler.setParameter("appid", WxPayConfig.APP_ID);
			// 微信支付分配的商户号
			reqHandler.setParameter("mch_id", WxPayConfig.MCH_ID);
			// 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
			reqHandler.setParameter("device_info", "WEB");
			// 随机字符串，不长于32位。推荐随机数生成算法
			reqHandler.setParameter("nonce_str", noncestr);
			// 商品描述
			reqHandler.setParameter("body", body);
			// 商家订单号
			reqHandler.setParameter("out_trade_no", out_trade_no);
			// 商品金额,以分为单位
			reqHandler.setParameter("total_fee", order_price);
			// APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
			reqHandler.setParameter("spbill_create_ip", "123.57.58.123");
			// 下面的notify_url是用户支付成功后为微信调用的action 异步回调.
			reqHandler.setParameter("notify_url", WxPayConfig.NOTIFY_URL);
			// 交易类型,取值如下：JSAPI，NATIVE，APP，详细说明见参数规定
			reqHandler.setParameter("trade_type", "JSAPI");
			// ------------需要进行用户授权获取用户openid-------------
			reqHandler.setParameter("openid", openid); // 这个必填.
			/*
			 * <xml><appid>wx2421b1c4370ec43b</appid><attach>支付测试</attach><body>
			 * JSAPI支付测试</body><mch_id>10000100</mch_id><nonce_str>
			 * 1add1a30ac87aa2db72f57a2375d8fec</nonce_str><notify_url>http://wxpay.
			 * weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url><openid>
			 * oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid><out_trade_no>1415659990</
			 * out_trade_no><spbill_create_ip>14.23.150.211</spbill_create_ip><
			 * total_fee>1</total_fee><trade_type>JSAPI</trade_type><sign>
			 * 0CB01533B8C1EF103065174F50BCA001</sign></xml>
			 */
			// 生成签名，并且转为xml
			String requestXml;
			requestXml = reqHandler.getRequestXml();

			System.out.println("requestXml:" + requestXml);

			// 得到的预支付id
			String prepay_id;
			prepay_id = ServiceUtil.unifiedorder(requestXml);

			// System.out.println("params:" + JSONObject.parse.toString());

			// 生成支付签名,这个签名 给 微信支付的调用使用
			SortedMap<Object, Object> signMap = new TreeMap<Object, Object>();
			signMap.put("appId", WxPayConfig.APP_ID);
			signMap.put("timeStamp", timestamp);
			signMap.put("nonceStr", noncestr);
			signMap.put("package", "prepay_id=" + prepay_id);
			signMap.put("signType", "MD5");

			// 微信支付签名
			String paySign = WeixinUtils.createSign(signMap, WxPayConfig.KEY);
			System.out.println("PaySIGN:" + paySign);

			// 微信分配的公众账号ID（企业号corpid即为此appId）
			JSONObject res = new JSONObject();

			res.put("appId", WxPayConfig.APP_ID);
			// 时间戳
			res.put("timeStamp", timestamp);
			// 随机字符串
			res.put("nonceStr", noncestr);
			// 预支付id ,就这样的格式
			res.put("pack_age", "prepay_id=" + prepay_id);
			// 加密格式
			res.put("signType", WxPayConfig.SIGN_TYPE);
			// 微信支付签名
			res.put("paySign", paySign);
			
			//调起支付，修改订单状态为支付
			
			
			return res.toString();
		} catch (UnsupportedEncodingException e1) {
			// TODO 打印输出日志
			FileLog.errorLog(e1,"获取微信预付单失败,不支持的编码格式");
			throw new RuntimeException();
		} catch (Exception e) {
			// TODO 打印输出日志
			FileLog.errorLog(e,"获取微信预付单失败,调用统一下单失败");
			throw new RuntimeException();
		}
	}

}
