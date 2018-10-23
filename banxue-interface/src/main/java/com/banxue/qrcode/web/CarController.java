package com.banxue.qrcode.web;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banxue.qrcode.entity.Car;
import com.banxue.qrcode.entity.User;
import com.banxue.qrcode.service.ICarService;
import com.banxue.qrcode.service.IUserService;
import com.banxue.utils.Constants;
import com.banxue.utils.R;
import com.banxue.utils.StringUtils;
import com.banxue.utils.log.FileLog;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	@PostMapping(value="/getUserCar",produces = "application/json;charset=UTF-8")
	@ResponseBody()
	public R getUserCar(String cardNo) {
		try{
			if(StringUtils.StrFilter(cardNo)) {
				return R.error("包含非法字符。");
			}
			Wrapper<Car> wra=new EntityWrapper<Car>();
			wra.eq("card_no", cardNo);
			List<Car> lst=carService.selectList(wra);
			List<Car> nlst=new ArrayList<Car>();
			for(Car c:lst) {
				Car nc=new Car();
				nc.setCarNo(c.getCarNo());
				nc.setUserPhone(StringUtils.HiddenPhone(c.getUserPhone()));
				nlst.add(nc);
			}
			return R.ok("成功", nlst);
		}catch(Exception e) {
			FileLog.errorLog(e);
		}
		return R.error();
	}
}
