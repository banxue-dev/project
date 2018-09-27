package com.banxue.qrcode.web;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.banxue.qrcode.entity.Car;
import com.banxue.qrcode.service.ICarService;
import com.banxue.utils.R;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

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
	@PostMapping("getUserCar")
	@ResponseBody
	public R getUserCar(String userPhone) {
		try{
			Wrapper<Car> wra=new EntityWrapper<Car>();
			wra.eq("userPhone", "13524954089");
			List<Car> lst=carService.selectList(wra);
			return R.ok(1, "", lst);
		}catch(Exception e) {
			
		}
		return null;
	}

}
