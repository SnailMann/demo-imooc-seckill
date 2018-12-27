package com.snailmann.seckill.service;

import com.snailmann.seckill.dao.GoodsMapper;
import com.snailmann.seckill.entity.BusinessGoods;
import com.snailmann.seckill.entity.Goods;
import com.snailmann.seckill.entity.dto.GoodsDto;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品业务层
 * @author liwenjie
 */
@Service
public class GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 查询商品的信息（包含秒杀信息）
     * @return
     */
    public List<GoodsDto> listGoodsDto(){
        return goodsMapper.listGoodsDto();
    }

    /**
     * 根据商品id获取商品信息(包含秒杀信息)
     * @param goodsId
     * @return
     */
    public GoodsDto getGoodsDto(String goodsId) {
        return goodsMapper.getGoodsDto(goodsId);
    }

    /**
     * 减库存操作
     * @param goodsDto
     */
    public void reduceStock(GoodsDto goodsDto) {
        goodsMapper.reduceStock(goodsDto.getId(),goodsDto.getStockCount() - 1);
    }
}
