package com.banxue.wxapi;

import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.banxue.QRCode.entity.User;
import com.banxue.QRCode.service.IUserService;
import com.banxue.onlinemail.entity.BxNuoGoods;
import com.banxue.onlinemail.service.IBxNuoGoodsService;
import com.banxue.utils.HttpUtils;
import com.banxue.utils.R;
import com.banxue.utils.StringUtils;
import com.banxue.utils.log.FileLog;
import com.banxue.utils.pay.wex.ServiceUtil;
import com.banxue.utils.wx.StrXmlToMap;
import com.banxue.utils.wx.WXBizMsgCrypt;
import com.banxue.utils.wx.WxConstants;
import com.banxue.utils.wx.WxMessageUtil;
import com.banxue.utils.wx.WxUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

/**
作者：fengchase
时间：2018年10月23日
文件：WxApiController.java
项目：banxue-frontend
*/
@Controller
@RequestMapping("/wx")
public class WxApiController {
	@Autowired
	private IUserService userService;
	@Value("${HeadUrlHead}")
	private String HeadUrlHead;
	@Autowired
	private IBxNuoGoodsService goodsService;
	/**
	 * 用于公众号验证服务器配置
	 * @param request
	 * @return
	 * 2019年2月27日
	 * 作者：fengchase
	 */
	@GetMapping("/wxService")
	@ResponseBody
	public String wxServiceGet(HttpServletRequest request) {
		try {
			String msgSignature = request.getParameter("signature");
			String msgTimestamp = request.getParameter("timestamp");
			String msgNonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");
			// 这里的 WXPublicConstants.TOKEN 填写你自己设置的Token就可以了
			if (WxUtils.verifyUrl(msgSignature, msgTimestamp, msgNonce)) {
				return echostr;
			}
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用于服务器消息交互
	 * @param request
	 * @return
	 * 2019年2月27日
	 * 作者：fengchase
	 */
	@PostMapping("/wxService")
	@ResponseBody
	public String wxServicePost(HttpServletRequest request) {
		try {
			WXBizMsgCrypt wx = WXBizMsgCrypt.getWxCrypt();
			// 从request中取得输入流
			InputStream inputStream = request.getInputStream();
			// 转为String
			String xml = StrXmlToMap.ISXmlToString(inputStream);
			// 解密
			String res = wx.decryptMsg(xml);
			return WxMessageUtil.responseMessage(res);
			// return wx.encryptMsg("已收到你的消息，马上前往支援。", DateUtils.format(new Date(),
			// DateUtils.DATE_TIME_STAMP), WxUtils.getRandomStr());
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取素材列表
	 * 
	 * 2019年2月16日
	 * 作者：fengchase
	 */
	public void addImageToWx() {
		JSONObject js=new JSONObject ();
		js.put("type", "image");
		js.put("offset", 0);
		js.put("count", 20);
		try {
			String s=HttpUtils.post("https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=18_evaGdEmT-6UWlO4oVaTn_OB24jVlO4fMl3o2FRph4NYbOPQPl7q9bYQSEcr5fjta63D5fg8O8bdnX2u-PTUfziH3Ptvid-Pzh5HDciJ-kWQUTVrbWNmHFRGmkafBoKLzIpAGAOC4Hh-7jiIxTDJiAIAEOU", js);
			System.out.println(s);
		} catch (Exception e) {
			// TODO 打印输出日志
			e.printStackTrace();
		}
	}
	/**
	 * 所有微信页面的统一跳转获取openid
	 * @param request
	 * @return
	 * 2019年2月27日
	 * 作者：fengchase
	 */
	@GetMapping("/sendWxSignJumpage")
	public String sendWxSignJumpage(HttpServletRequest request,String jumpAddr) {
		try {
			request.setAttribute("jumpAddr", jumpAddr);
			String decodeUrl=URLDecoder.decode(jumpAddr,"UTF-8");
			return "wxjump";
		} catch (Exception e) {
			// TODO 打印输出日志
			FileLog.errorLog("异常，原因："+e);
			return "404";
		}
	}
	/**
	 * 接收到返回的数据，并且保存用户，并前往指定页面
	 * @param request
	 * @return state参数是要前往的地址。
	 * 2019年2月27日
	 * 作者：fengchase
	 */
	@GetMapping("/getWxSignJumpage")
	public String getWxSignJumpage(HttpServletRequest request) {
		try {
			String wxcode = request.getParameter("code");
			String state=request.getParameter("state");
			String openId;
			String access_token;
			JSONObject openJson = ServiceUtil.getOpenId(wxcode);
			openId = openJson.getString("openid");
			access_token = openJson.getString("access_token");
			WxConstants.WxToken=access_token;
			Wrapper<User> wrapper = new EntityWrapper<User>();
			wrapper.where("wx_opend_id ={0}", openId);
			request.setAttribute("openid", openId);
			User user = userService.selectOne(wrapper);
			if(user==null || user.getWxOpendId()==null) {
				/*
				 * 没有这个用户，新增
				 */
				// 获取微信用户的数据
				JSONObject wxuser = ServiceUtil.getUserInfo(access_token, openId, wxcode, 0);
				if(wxuser==null) {
					FileLog.debugLog("获取微信用户为空。");
					return "404";
				}
				// 保存用户数据
				User iu = new User();
				iu.setCreateTime(new Date());
				iu.setNickName(wxuser.getString("nickname"));
				iu.setModTime(new Date());
				iu.setUserHeadUrl(wxuser.getString("headimgurl"));
				iu.setUserCity(wxuser.getString("city"));
				iu.setUserProvince(wxuser.getString("province"));
				iu.setUserSexual(wxuser.getString("sex"));
				iu.setTokenModTime(new Date());
				iu.setUserToken(access_token);
				iu.setWxOpendId(openId);
				userService.insert(iu);
				user=iu;
			}
			// 有这个用户，直接前往我的界面
			
			String headurl=HeadUrlHead+(user.getUserHeadUrl()==null?"zuofei.jpg":user.getUserHeadUrl());
			if(StringUtils.Str1ConstansStr2(user.getUserHeadUrl(), "http")) {
				headurl=user.getUserHeadUrl();
			}
			request.setAttribute("headurl", headurl);
			request.setAttribute("nickname", user.getNickName());
			request.setAttribute("message", user.getUserMessage());
			request.setAttribute("openid", openId);
//			request.setAttribute("accessToken", access_token);
			String decodeUrl=URLDecoder.decode(state,"UTF-8");
			this.fullDataInPage(decodeUrl,request);
			return decodeUrl;
		} catch (Exception e) {
			// TODO 打印输出日志
			FileLog.errorLog("异常，原因："+e);
			return "404";
		}
	}
	/**
	 * 为前往页面填充地址
	 * @param addr
	 * 2019年3月4日
	 * 作者：fengchase
	 */
	public void fullDataInPage(String addr,HttpServletRequest request) {
		try {
			if(StringUtils.twoStrMatch(addr, "onliemail/prodetail")) {
				/**
				 * 获取商品数据
				 */
//				Wrapper<BxNuoGoods> wra=new EntityWrapper<BxNuoGoods>();
				BxNuoGoods bng= goodsService.selectById(1);
				request.setAttribute("goods", bng);
				/*String imgs=bng.getGoodsImgs();
				if(StringUtils.isNullString(imgs)) {
					
				}*/
				request.setAttribute("imgs", bng.getGoodsImgs().split(","));
			}else if(StringUtils.twoStrMatch(addr, "test/qieclear")) {
//				WxUtils.getJsapiTicket(request.getAttribute("accessToken").toString());
			}
		}catch(Exception e) {
			FileLog.errorLog("获取数据异常："+e);;
			
		}
	}
}

