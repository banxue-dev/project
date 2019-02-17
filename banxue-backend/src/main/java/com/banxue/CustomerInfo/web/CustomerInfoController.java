package com.banxue.CustomerInfo.web;


import org.springframework.web.bind.annotation.RequestMapping;

import com.banxue.CustomerInfo.entity.CustomerInfo;
import com.banxue.utils.R;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Feng
 * @since 2019-01-28
 */
@Controller
@RequestMapping("/customerInfo")
public class CustomerInfoController {
	
	public R customerMessage(CustomerInfo ci) {
		try {
			
			CustomerInfo ci=new CustomerInfo();
			ci.
			
			return R.ok();
		}catch(Exception e) {
			
			return R.error();
		}
	}

}
