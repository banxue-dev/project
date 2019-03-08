package com.banxue.onlinemail.service;

import com.banxue.onlinemail.entity.BxNuoAddr;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Feng
 * @since 2019-02-21
 */
public interface IBxNuoAddrService extends IService<BxNuoAddr> {
	void addNewAddress(BxNuoAddr bxn);

	void modNewAddress(BxNuoAddr bxn);

}
