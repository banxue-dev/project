package com.banxue.qrcode.service.impl;

import com.banxue.qrcode.entity.User;
import com.banxue.qrcode.mapper.UserMapper;
import com.banxue.qrcode.service.IUserService;
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

}
