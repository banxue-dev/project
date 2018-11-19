package com.banxue.QRCode.service.impl;

import com.banxue.QRCode.entity.Car;
import com.banxue.QRCode.mapper.CarMapper;
import com.banxue.QRCode.service.ICarService;
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
