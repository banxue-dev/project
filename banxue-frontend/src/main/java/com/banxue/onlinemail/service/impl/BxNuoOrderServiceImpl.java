package com.banxue.onlinemail.service.impl;

import com.banxue.onlinemail.entity.BxNuoOrder;
import com.banxue.onlinemail.mapper.BxNuoOrderMapper;
import com.banxue.onlinemail.service.IBxNuoOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Feng
 * @since 2019-02-21
 */
@Service
public class BxNuoOrderServiceImpl extends ServiceImpl<BxNuoOrderMapper, BxNuoOrder> implements IBxNuoOrderService {

	@Override
	public String addWxNewOrder(BxNuoOrder order, Long goodsId, String openid, Integer orderAddrId, Double siglePrice) {
		// TODO 此处为方法主题
		return null;
	}

}
