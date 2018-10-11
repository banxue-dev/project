package com.banxue.qrcode.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banxue.qrcode.entity.Car;
import com.banxue.qrcode.service.ICarService;
import com.banxue.utils.R;
import com.banxue.utils.StringUtils;
import com.banxue.utils.log.FileLog;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feng
 * @since 2018-09-27
 */
@Controller
@RequestMapping("/car")
public class CarController {
	
	@Autowired
	private ICarService carService;
	@RequestMapping("/getUserCar")
	@ResponseBody
	public R getUserCar(String userPhone) {
		try{
			if(StringUtils.StrFilter(userPhone)) {
				return R.error("包含非法字符。");
			}
			Wrapper<Car> wra=new EntityWrapper<Car>();
			wra.eq("user_phone", "13524954089");
			List<Car> lst=carService.selectList(wra);
			return R.ok(1, "", lst);
		}catch(Exception e) {
			FileLog.errorLog(e);
		}
		return R.error();
	}

}
