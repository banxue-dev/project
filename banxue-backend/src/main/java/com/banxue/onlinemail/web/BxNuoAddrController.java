package com.banxue.onlinemail.web;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banxue.onlinemail.entity.BxNuoAddr;
import com.banxue.onlinemail.service.IBxNuoAddrService;
import com.banxue.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
	@PostMapping("add")
	@ResponseBody
	public R addNewAddress(BxNuoAddr newAddr) {
		try {
			addrService.insert(newAddr);
			return R.ok();
		}catch(Exception e) {
			return R.error();
		}
	}

}
