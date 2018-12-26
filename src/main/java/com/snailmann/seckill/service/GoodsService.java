package com.snailmann.seckill.service;

import com.snailmann.seckill.dao.GoodsMapper;
import com.snailmann.seckill.entity.dto.GoodsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    public List<GoodsDto> listSeckillGoods(){
        return goodsMapper.listSeckillGoods();
    }
}
