package com.banxue.qrcode.service.impl;

import com.banxue.qrcode.entity.User;
import com.banxue.qrcode.mapper.UserMapper;
import com.banxue.qrcode.service.IUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feng
 * @since 2018-09-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Override
	public User getUserByOpenId(String openId) {
		// TODO 此处为方法主题
		Wrapper<User> uw = new EntityWrapper<User>();
		uw.addFilter("wx_opend_id", openId);
		return this.selectOne(uw);
	}

}
