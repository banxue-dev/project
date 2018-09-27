package com.banxue.qrcode.service.impl;

import com.banxue.qrcode.entity.Car;
import com.banxue.qrcode.mapper.CarMapper;
import com.banxue.qrcode.service.ICarService;
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
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements ICarService {

}
