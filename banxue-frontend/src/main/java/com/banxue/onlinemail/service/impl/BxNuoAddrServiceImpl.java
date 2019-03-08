package com.banxue.onlinemail.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banxue.onlinemail.entity.BxNuoAddr;
import com.banxue.onlinemail.mapper.BxNuoAddrMapper;
import com.banxue.onlinemail.service.IBxNuoAddrService;
import com.banxue.utils.log.FileLog;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Feng
 * @since 2019-02-21
 */
@Service
public class BxNuoAddrServiceImpl extends ServiceImpl<BxNuoAddrMapper, BxNuoAddr> implements IBxNuoAddrService {

	@Autowired
	private BxNuoAddrMapper bxNuoAddrDao;
	@Override
	public void addNewAddress(BxNuoAddr bxn) {
		// TODO 此处为方法主题
		if(bxn==null) {
			FileLog.errorLog("BxNuoAddr对象为空。");
			throw new RuntimeException("对象为空。");
		}
		bxn.setCreateTime(new Date());
		bxNuoAddrDao.insert(bxn);
	}
	@Override
	public void modNewAddress(BxNuoAddr bxn) {
		// TODO 此处为方法主题
		if(bxn==null) {
			FileLog.errorLog("BxNuoAddr对象为空。");
			throw new RuntimeException("对象为空。");
		}
		bxn.setUpdateTime(new Date());
		bxNuoAddrDao.updateById(bxn);
	}

}
