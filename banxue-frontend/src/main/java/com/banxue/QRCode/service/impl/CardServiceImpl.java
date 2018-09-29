package com.banxue.QRCode.service.impl;

import com.banxue.QRCode.entity.Card;
import com.banxue.QRCode.mapper.CardMapper;
import com.banxue.QRCode.service.ICardService;
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
public class CardServiceImpl extends ServiceImpl<CardMapper, Card> implements ICardService {

}
