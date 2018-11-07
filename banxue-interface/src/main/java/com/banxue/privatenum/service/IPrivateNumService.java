package com.banxue.privatenum.service;

import java.util.List;

import com.banxue.privatenum.entity.PrivateNum;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mht
 * @since 2018-11-07
 */
public interface IPrivateNumService extends IService<PrivateNum> {

	List<PrivateNum> getListByState(Integer state);
}
