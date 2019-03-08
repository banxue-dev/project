package com.banxue.onlinemail.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.banxue.onlinemail.entity.BxNuoAddr;
import com.banxue.onlinemail.entity.BxNuoGoods;
import com.banxue.onlinemail.entity.BxNuoOrder;
import com.banxue.onlinemail.entity.BxNuoOrderByGoods;
import com.banxue.onlinemail.entity.BxNuoWuliu;
import com.banxue.onlinemail.service.IBxNuoAddrService;
import com.banxue.onlinemail.service.IBxNuoGoodsService;
import com.banxue.onlinemail.service.IBxNuoOrderByGoodsService;
import com.banxue.onlinemail.service.IBxNuoOrderService;
import com.banxue.onlinemail.service.IBxNuoWuliuService;
import com.banxue.utils.Constants;
import com.banxue.utils.HttpUtils;
import com.banxue.utils.OrderNoCreater;
import com.banxue.utils.R;
import com.banxue.utils.StringUtils;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.pay.PayUtils;
import com.banxue.utils.pay.pojo.PayOrderDO;
import com.banxue.utils.pay.pojo.RequestHandler;
import com.banxue.utils.pay.wex.WxPayConfig;
import com.banxue.utils.wx.StrXmlToMap;
import com.banxue.utils.wx.WxUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Feng
 * @since 2019-02-21
 */
@Controller
@RequestMapping("/bxNuoOrder")
public class BxNuoOrderController {

	@Autowired
	private IBxNuoGoodsService bxNuoGoodsService;
	@Autowired
	private IBxNuoAddrService bxNuoAddrService;
	@Autowired
	private IBxNuoOrderService bxNuoOrderService;
	@Autowired
	private IBxNuoOrderByGoodsService bxNuoOrderByGoodsService;
	@Autowired
	private IBxNuoWuliuService bxNuoWuliuService;

	/**
	 * 添加订单，并拉取预支付信息
	 * 
	 * @param request
	 * @return 2019年2月27日 作者：fengchase
	 */
	@PostMapping("/getPreData")
	@ResponseBody
	public R addOrder(BxNuoOrder order, Long goodsId, String openid, Integer orderAddrId,Integer buyCount) {
		try {
			if(StringUtils.isNullString(order.getOrderCode())) {
				buyCount=buyCount==null || buyCount<1?1:buyCount;
				BxNuoGoods goods=bxNuoGoodsService.selectById(goodsId);
				order.setOrderCode(OrderNoCreater.CreateOrderNo());
				order.setOrderStatus(Constants.ORDER_STATE_PAYING);
				order.setOrderAddrId(orderAddrId);
				order.setCreateTime(new Date());
				order.setBuyUserOpenid(openid);
				order.setTotailPrice(goods.getGoodsPrice()*buyCount);
				bxNuoOrderService.insert(order);
				BxNuoOrderByGoods nobg = new BxNuoOrderByGoods();
				nobg.setBuyCount(buyCount);
				nobg.setGoodsId(goodsId);
				nobg.setBuyPrice(goods.getGoodsPrice());
				nobg.setOrderCode(order.getOrderCode());
				bxNuoOrderByGoodsService.insert(nobg);
			}else {
				Wrapper<BxNuoOrder> wrapper=new EntityWrapper<BxNuoOrder>();
				wrapper.eq("order_code", order.getOrderCode());
				order=bxNuoOrderService.selectOne(wrapper);
			}
			PayOrderDO payOrder = new PayOrderDO();
			payOrder.setOrderNo(order.getOrderCode());
//			payOrder.setPayPrice(order.getTotailPrice());
			payOrder.setPayPrice(0.01D);
			payOrder.setOrderDesc(order.getOrderRemark());
			payOrder.setOpenid(openid);
			String wxpreOrder = PayUtils.getWexPayOrderInfos(payOrder);
			return R.okdata(wxpreOrder);
		} catch (Exception e) {
			// TODO 打印输出日志
			FileLog.debugLog("创建订单异常。原因：" + e);
			return R.error("创建订单异常");
		}
	}

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

			FileLog.debugLog("微信回调结果:" + resultMap.toString());

			String result_code = resultMap.get("result_code");
			String orderNo = resultMap.get("out_trade_no");
			// String is_subscribe = resultMap.get("is_subscribe");
			// String transaction_id = resultMap.get("transaction_id");
			// String sign = resultMap.get("sign");
			// String time_end = resultMap.get("time_end");
			// String bank_type = resultMap.get("bank_type");
			String return_code = resultMap.get("return_code");
			Wrapper<BxNuoOrder> wra = new EntityWrapper<BxNuoOrder>();
			wra.eq("order_code", orderNo);
			BxNuoOrder order = bxNuoOrderService.selectOne(wra);
			// 签名验证
			// GenericValue userLogin = delegator.findOne("UserLogin",
			// UtilMisc.toMap("userLoginId", "admin"), false);

			if (return_code.equals("SUCCESS")) {
				if (result_code.equals("SUCCESS")) {
					order.setOrderStatus(Constants.ORDER_STATE_PAYS);
				} else {
					order.setOrderStatus(Constants.ORDER_STATE_DEAD);
				}
				// 修改数据库支付状态
			} else {
				// 交易失败，支付失败
				order.setOrderStatus(Constants.ORDER_STATE_DEAD);
			}
			order.setOrderStatus(Constants.ORDER_STATE_PAYS);
			// 保存交易记录
			order.setUpdateTime(new Date());
			bxNuoOrderService.updateById(order);
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
	@RequestMapping(value = "/getOrderList")
	@ResponseBody
	public R getOrderList(Integer state,String openid) {
		try {
			Wrapper<BxNuoOrder> wrapper=new EntityWrapper<BxNuoOrder>();
			wrapper.eq("buy_user_openid", openid);
			/**
			 * 0表示查询所有
			 */
			if(state!=0) {
				wrapper.eq("order_status", state);
				List<String> cols=new ArrayList<String>();
				cols.add("create_time");
				wrapper.orderDesc(cols);
			}
			List<BxNuoOrder> lst=bxNuoOrderService.selectList(wrapper);
			for(BxNuoOrder order:lst) {
				List<BxNuoGoods> lstgoods=new ArrayList<BxNuoGoods>();
				Wrapper<BxNuoOrderByGoods> wrag=new EntityWrapper<BxNuoOrderByGoods>();
				wrag.eq("order_code", order.getOrderCode());
				List<BxNuoOrderByGoods> bnobg=bxNuoOrderByGoodsService.selectList(wrag);
				for(BxNuoOrderByGoods bygoods:bnobg) {
					BxNuoGoods bgoods=bxNuoGoodsService.selectById(bygoods.getGoodsId());
					lstgoods.add(bgoods);
				}
				order.setGoods(lstgoods);
			}
			return R.okdata(lst);
		}catch(Exception e) {
			FileLog.debugLog("获取失败，原因："+e);
			return R.error();
		}
	}
	@RequestMapping(value = "/toOrderListPage/")
	public String toOrderListPage(String openid,HttpServletRequest request) {
		try {
			request.setAttribute("openid", openid);
			return "onliemail/myorder";
		}catch(Exception e) {
			return "500";
		}
	}
	@RequestMapping(value = "/selectOrderWuliu")
	public String selectOrderWuliu(HttpServletRequest request,Long wuliuid) {
		try {
			/*
			 * 获取物流信息
			 */
			Wrapper<BxNuoWuliu> wrapper=new EntityWrapper<BxNuoWuliu>();
			BxNuoWuliu wuliu= bxNuoWuliuService.selectById(wuliuid);
			//?type=shunfeng&postid=708546710852&temp=456&phone=4089
			String param="";
			param+="type="+wuliu.getWuliuCompanCode();
			param+="&postid="+wuliu.getWuliuCode();
			param+="&temp="+Math.random();
			param+="&phone="+wuliu.getWuliuPhoneLast();
			FileLog.debugLog("物流请求参数是："+param);
			String result=HttpUtils.sendGET("http://www.kuaidi100.com/query", param);
			JSONObject jb=JSONObject.parseObject(result);
			if(StringUtils.twoStrMatch(jb.getString("message"), "ok")) {
				request.setAttribute("companyName", wuliu.getWuliuCompanName());
				request.setAttribute("wuliuno", wuliu.getWuliuCode());
				request.setAttribute("datas", jb.getJSONArray("data"));
			}else {
				request.setAttribute("companyName", "等待发货");
				request.setAttribute("wuliuno", "等待发货");
				FileLog.debugLog("获取失败，返回内容："+result);
			}
			return "onliemail/wuliu";
		}catch(Exception e) {
			FileLog.debugLog("异常，原因："+e);
			return "500";
		}
	}
	/**
	 * 添加订单，并拉取预支付信息
	 * 
	 * @param request
	 * @return 2019年2月27日 作者：fengchase
	 */
	@PostMapping("/addOrder")
	@ResponseBody
	public R toAddOrder(BxNuoOrder order, Long goodsId, String openid,
			Double siglePrice) {
		try {
			order.setOrderCode(OrderNoCreater.CreateOrderNo());
			order.setOrderStatus(Constants.ORDER_STATE_NEW);
			order.setCreateTime(new Date());
			order.setBuyUserOpenid(openid);
			bxNuoOrderService.insert(order);
			BxNuoOrderByGoods nobg = new BxNuoOrderByGoods();
			nobg.setBuyCount(1);
			nobg.setGoodsId(goodsId);
			nobg.setBuyPrice(siglePrice);
			nobg.setOrderCode(order.getOrderCode());
			bxNuoOrderByGoodsService.insert(nobg);
			JSONObject res=new JSONObject();
			res.put("orderCode", order.getOrderCode());
			res.put("openid", openid);
			return R.okdata(res);
		} catch (Exception e) {
			// TODO 打印输出日志
			FileLog.debugLog("创建订单异常。原因：" + e);
			return R.error();
		}
	}
	/**
	 * 前往支付页面
	 * 
	 * @param request
	 * @return 2019年2月27日 作者：fengchase
	 */
	@RequestMapping("/toPayPage/")
	public String toPayPage(String goodsid, String openid,HttpServletRequest request,String addrId) {
		try {
			if(!StringUtils.isNullString(addrId)) {
				BxNuoAddr bna= bxNuoAddrService.selectById(addrId);
				request.setAttribute("addr", bna);
			}else {
				Wrapper<BxNuoAddr> wra=new EntityWrapper<BxNuoAddr>();
				wra.eq("is_deafualt", 0);
				BxNuoAddr bna=bxNuoAddrService.selectOne(wra);
				request.setAttribute("addr", bna);
			}
			BxNuoGoods goods= bxNuoGoodsService.selectById(goodsid);
			request.setAttribute("openid", openid);
			request.setAttribute("goods", goods);
			return "onliemail/index";
		} catch (Exception e) {
			// TODO 打印输出日志
			FileLog.debugLog("创建订单异常。原因：" + e);
			return "500";
		}
	}
}
