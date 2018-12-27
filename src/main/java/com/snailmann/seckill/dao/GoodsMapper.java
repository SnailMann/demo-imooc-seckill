package com.snailmann.seckill.dao;

import com.snailmann.seckill.entity.BusinessGoods;
import com.snailmann.seckill.entity.Goods;
import com.snailmann.seckill.entity.dto.GoodsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 商品Dao层
 * @author liwenjie
 */
@Mapper
public interface GoodsMapper {

    /**
     * 遍历获取商品的信息集合（商品本身信息 + 商品的秒杀信息）
     * @return
     */
    @Select("select g.*,bg.seckill_price,bg.stock_count,bg.start_date,bg.end_date " +
            "from seckill_business_goods bg left join seckill_goods g " +
            "on bg.goods_id = g.id;")
    List<GoodsDto> listGoodsDto();

    /**
     * 根据商品id查询商品的信息集合（商品本身信息 + 商品的秒杀信息）
     * @param goodsId
     * @return
     */
    @Select("select g.*,bg.seckill_price,bg.stock_count,bg.start_date,bg.end_date " +
            "from seckill_business_goods bg inner join seckill_goods g " +
            "on bg.goods_id = g.id and g.id = #{goodsId};")
    GoodsDto getGoodsDto(@Param("goodsId") String goodsId);

    /**
     * 根据商品id来减库存
     * @param goodsId
     * @param stockCount
     */
    @Update("update seckill_business_goods set stock_count = #{stockCount} where goods_id = #{goodsId} and stock_count > 0")
    void reduceStock(@Param("goodsId") Long goodsId,@Param("stockCount") Integer stockCount);
}
