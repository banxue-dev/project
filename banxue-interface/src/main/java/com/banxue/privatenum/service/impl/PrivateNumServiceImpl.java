package com.banxue.privatenum.service.impl;

import com.banxue.privatenum.entity.PrivateNum;
import com.banxue.privatenum.mapper.PrivateNumMapper;
import com.banxue.privatenum.service.IPrivateNumService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mht
 * @since 2018-11-07
 */
@Service
public class PrivateNumServiceImpl extends ServiceImpl<PrivateNumMapper, PrivateNum> implements IPrivateNumService {

	@Autowired
	private PrivateNumMapper privateNumMapper;
	/**
	 * 根据状态获取对应的电话列表数据。
	 */
	@Override
	public List<PrivateNum> getListByState(Integer state) {
		// TODO 此处为方法主题
		Wrapper<PrivateNum> wra = new EntityWrapper<PrivateNum>();
		wra.where("state={0}",state);
		return privateNumMapper.selectList(wra);
	}
	@Override
	public PrivateNum getPrivateNumByUserPhone(String phone) {
		// TODO 此处为方法主题
		PrivateNum pn=new PrivateNum();
		pn.setUserPhone(phone);
		return privateNumMapper.selectOne(pn);
	}

}
