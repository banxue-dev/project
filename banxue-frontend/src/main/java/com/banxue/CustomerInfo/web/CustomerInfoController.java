package com.banxue.CustomerInfo.web;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banxue.CustomerInfo.entity.CustomerInfo;
import com.banxue.CustomerInfo.service.ICustomerInfoService;
import com.banxue.utils.R;
import com.banxue.utils.StringUtils;
import com.banxue.utils.log.FileLog;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private ICustomerInfoService customerInfoService;
	
	@PostMapping("/add")
	@ResponseBody
	public R customerMessage(CustomerInfo ci,String code) {
		try {
			if(StringUtils.isNullString(code)) {
				return R.error();
			}
			if(StringUtils.twoStrMatch(code.toLowerCase(), "xdn4")) {
				ci.setCreateTime(new Date());
				customerInfoService.insert(ci);
				return R.ok();
			}else {
				return R.error("验证码不正确");
			}
			
		}catch(Exception e) {
			FileLog.errorLog(e,"添加客户出现错误。");
			return R.error("异常");
		}
	}
}
