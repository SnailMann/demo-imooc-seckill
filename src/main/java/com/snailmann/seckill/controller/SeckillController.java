package com.snailmann.seckill.controller;

import com.snailmann.seckill.entity.BusinessOrder;
import com.snailmann.seckill.entity.Order;
import com.snailmann.seckill.entity.User;
import com.snailmann.seckill.entity.dto.GoodsDto;
import com.snailmann.seckill.constant.CodeMsg;
import com.snailmann.seckill.service.GoodsService;
import com.snailmann.seckill.service.OrderService;
import com.snailmann.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品控制层
 * @author liwenjie
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    SeckillService seckillService;

    /**
     * 根据商品Id对某商品进行秒杀操作
     *
     * @param user 根据token获取用户信息
     * @param model
     * @param goodsId 商品id
     * @return
     */
    @RequestMapping("/do")
    public String doSeckill(User user, Model model, @RequestParam String goodsId) {
        model.addAttribute("user", user);
        //判断用户是否是空，如果是空，则返回登录页面
        if (user == null){
            return "login";
        }
        //1. 第一判断是否有库存
        GoodsDto goodsDto = goodsService.getGoodsDto(goodsId);
        //获得库存
        int stockCount = goodsDto.getStockCount();
        if (stockCount <= 0 ){
            //如果没有，则代表该商品已经没有库存，已经被秒杀完了
            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
            return "seckill-fail";
        }

        //2. 如果有库存则判断是否已经秒杀到了
        //根据用户id和商品id获取是否有秒杀订单生产
        BusinessOrder businessOrder = orderService.getSeckillOrder(goodsId,user.getId());
        if (businessOrder != null){
            //如果已经存在，则说明重复秒杀了，不允许重复秒杀
            model.addAttribute("errmsg",CodeMsg.REPEATE_SECKILL.getMsg());
            return "seckill-fail";
        }

        //3. 以上异常判断完毕，则代表可以进行秒杀
        //减库存，下订单，写入秒杀订单
        Order order = seckillService.doSeckill(user,goodsDto);
        model.addAttribute("order", order);
        model.addAttribute("goodsDto", goodsDto);

        return "order-detail";

    }

}
