package com.banxue.onlinemail.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banxue.onlinemail.entity.BxNuoAddr;
import com.banxue.onlinemail.service.IBxNuoAddrService;
import com.banxue.utils.R;
import com.banxue.utils.log.FileLog;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Feng
 * @since 2019-02-21
 */
@Controller
@RequestMapping("/bxNuoAddr")
public class BxNuoAddrController {

	@Autowired
	private IBxNuoAddrService addrService;
	
	/**
	 * 
	 * 新增一个收货地址
	 * @param newAddr
	 * @return
	 * 2019年2月22日
	 * 作者：fengchase
	 */
	@PostMapping(value="add", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public R addNewAddress(BxNuoAddr newAddr) {
		try {
			/*
			 * 把之前默认的地址改为非默认
			 */
			if(newAddr.getIsDeafualt()==0) {
				Wrapper<BxNuoAddr> wra=new EntityWrapper<BxNuoAddr>();
				wra.eq("is_deafualt", 0);
				wra.eq("user_openid", newAddr.getUserOpenid());
				BxNuoAddr bna=addrService.selectOne(wra);
				bna.setIsDeafualt(1);
				addrService.updateById(bna);
			}
			addrService.addNewAddress(newAddr);
			return R.ok();
		}catch(Exception e) {
			return R.error();
		}
	}
	/**
	 * 
	 * 新增一个收货地址
	 * @param newAddr
	 * @return
	 * 2019年2月22日
	 * 作者：fengchase
	 */
	@PostMapping(value="mod", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public R modNewAddress(BxNuoAddr newAddr) {
		try {
			/*
			 * 把之前默认的地址改为非默认
			 */
			if(newAddr.getIsDeafualt()==0) {
				Wrapper<BxNuoAddr> wra=new EntityWrapper<BxNuoAddr>();
				wra.eq("is_deafualt", 0);
				wra.eq("user_openid", newAddr.getUserOpenid());
				BxNuoAddr bna=addrService.selectOne(wra);
				bna.setIsDeafualt(1);
				addrService.updateById(bna);
			}
			addrService.updateById(newAddr);
			return R.ok();
		}catch(Exception e) {
			return R.error();
		}
	}
	/**
	 * 
	 * 获取列表
	 * @param newAddr
	 * @return
	 * 2019年2月22日
	 * 作者：fengchase
	 */
	@PostMapping(value="getList", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public R getNewAddressList(String openid) {
		try {
//			Wrapper<User> wrapper = new EntityWrapper<User>();
//			wrapper.where("wx_opend_id ={0}", openId);
//			request.setAttribute("openid", openId);
			Wrapper<BxNuoAddr> bna=new EntityWrapper<BxNuoAddr>();
			bna.eq("user_openid", openid);
			List<BxNuoAddr> lstBxns=addrService.selectList(bna);
			return R.okdata(lstBxns);
		}catch(Exception e) {
			FileLog.errorLog(e);
			return R.error();
		}
	}
	/**
	 * 
	 * 获取一个收货地址
	 * @param newAddr
	 * @return
	 * 2019年2月22日
	 * 作者：fengchase
	 */
	@PostMapping(value="getAddr", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public R getNewAddress(String openid,Integer addrId) {
		try {
			Wrapper<BxNuoAddr> bna=new EntityWrapper<BxNuoAddr>();
			bna.eq("addr_id", addrId);
			BxNuoAddr res=addrService.selectOne(bna);
			return R.okdata(res);
		}catch(Exception e) {
			FileLog.errorLog("异常，原因："+e);
			return R.error();
		}
	}
	/**
	 * 前往新增地址
	 * @return
	 * 2019年3月4日
	 * 作者：fengchase
	 */
	@GetMapping("toNewAdd")
	public String toNewAdd(HttpServletRequest request,String openid,String goodsId) {
		request.setAttribute("openid", openid);
		request.setAttribute("goodsId", goodsId);
		
		return "onliemail/addAddr";
	}
	/**
	 * 前往List页面
	 * @return
	 * 2019年3月4日
	 * 作者：fengchase
	 */
	@GetMapping("toListPage")
	public String toList(HttpServletRequest request,String openid,String goodsId) {
		request.setAttribute("openid", openid);
		request.setAttribute("goodsId", goodsId);
		Wrapper<BxNuoAddr> bna=new EntityWrapper<BxNuoAddr>();
		bna.eq("user_openid", openid);
		List<BxNuoAddr> lstBxns=addrService.selectList(bna);
		request.setAttribute("datas", lstBxns);
		return "onliemail/addrList";
	}
}
