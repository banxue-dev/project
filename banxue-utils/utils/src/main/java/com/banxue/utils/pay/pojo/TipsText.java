package com.banxue.utils.pay.pojo;
/**
作者：fengchase
时间：2019年2月21日
文件：TipsController.java
项目：utils
*/
public class TipsText {

	/**
	 * 微信回调
	 * 
	 * @param request
	 * @return 2018年7月23日 作者：fengchase
	 */
	/*
	@RequestMapping(value = "/wex/callBack")
	@ResponseBody
	public String wexCallBack(HttpServletRequest request, HttpServletResponse response) {
		try {
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			String resultStr = new String(outSteam.toByteArray(), WxPayConfig.CHARTSET);
			Map<String, String> resultMap = StrXmlToMap.strXmltoMap(resultStr);

			System.out.println("微信回调结果:" + resultMap.toString());

			String result_code = resultMap.get("result_code");
			String orderNo = resultMap.get("out_trade_no");
			// String is_subscribe = resultMap.get("is_subscribe");
			// String transaction_id = resultMap.get("transaction_id");
			// String sign = resultMap.get("sign");
			// String time_end = resultMap.get("time_end");
			// String bank_type = resultMap.get("bank_type");
			String return_code = resultMap.get("return_code");

			QcPayOrderDO qo = qcPayOrderService.getOrderByOrderNumber(orderNo);
			// 签名验证
			// GenericValue userLogin = delegator.findOne("UserLogin",
			// UtilMisc.toMap("userLoginId", "admin"), false);
			PayRecordDO prd = new PayRecordDO();
			prd.setPayMoney(qo.getPayPrice());
			prd.setOrderNo(orderNo);
			prd.setPayType(qo.getPayType());
			prd.setPayDeviceNo(qo.getOrderNo());
			prd.setPayUserNo(qo.getPayUser());
			if (return_code.equals("SUCCESS")) {
				if (result_code.equals("SUCCESS")) {
					prd.setPayResult(0);
					qo.setOrderState(2);
				} else {
					prd.setPayResult(1);
					//失效
					qo.setOrderState(4);
				}
				// 修改数据库支付状态
			} else {
				// 交易失败，支付失败
				prd.setPayResult(1);
			}
			// 保存交易记录
			qcPayOrderService.update(qo);
			PpayRecordService.save(prd);
			request.setAttribute("out_trade_no", orderNo);
			// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return RequestHandler.setXML("SUCCESS", "");
		
}
*/
	/*
	<script>
	//防止重复点击
	var submit = false;

	$(document).ready(
			function() {
				$('#_loading_feng1').hide();
				$('.click_li').click(function(t) {
					$('.click_li').removeClass('selected');
					$(this).addClass('selected');
					var price = $(this).attr('price');
					$('.rechnum').html(price + '.00 元');
				});
				//绑定微信事件
				if (typeof WeixinJSBridge == "undefined") {
					if (document.addEventListener) {
						document.addEventListener('WeixinJSBridgeReady',
								onBridgeReady, false);
					} else if (document.attachEvent) {
						document.attachEvent('WeixinJSBridgeReady',
								onBridgeReady);
						document.attachEvent('onWeixinJSBridgeReady',
								onBridgeReady);
					}
				} else {
					onBridgeReady();
				}
			});
	function toPay() {
		$('#container').text('正在支付...');
		_loading.show();
		if (submit) {
			return;
		}
		submit = true;
		//传递code和state去addOrder增加订单
		var price = $('.selected').attr('price');
		var order = {};
		order.payType = 2;
		order.payPrice = '1';
		order.orderRemark = '充值燃气费';
		order.openid = $('#openid').val();
		order.payUser = $('#nickname').val();
		//获取数据后，拉起支付界面
		$.ajax({
			type : "POST",
			url : "addOrder",
			async : true,
			data : order,
			success : function(data) {
				onBridgeReady(data.data);
			}
		});
	}
	function onBridgeReady(data) {
		
			//拉起支付
		var payd = eval('(' + data + ')');
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
			"appId" : payd.appId,
			"timeStamp" : payd.timeStamp,
			"nonceStr" : payd.nonceStr,
			"package" : payd.pack_age,
			"signType" : payd.signType,
			"paySign" : payd.paySign
		}, function(res) {
			$('#container').text('立即支付');
			submit = false;
			_loading.hide();
			if (res.err_msg == "get_brand_wcpay_request:ok") {
				//调用设备写信息
				_loading.show();
				window.location.href = 'http://app.cdqckj.com/wx/ble/pubBind';
			} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
				alert("用户取消支付");
			} else {
				alert("支付失败");
			}
		});
	}
	//支付结束
</script>
*/
}

