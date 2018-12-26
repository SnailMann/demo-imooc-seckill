package com.snailmann.seckill.dao;

import com.snailmann.seckill.entity.dto.GoodsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsMapper {

    @Select("select g.*,bg.seckill_price,bg.stock_count,bg.start_date,bg.end_date " +
            "from seckill_business_goods bg left join seckill_goods g " +
            "on bg.goods_id = g.id;")
    List<GoodsDto> listSeckillGoods();

}
