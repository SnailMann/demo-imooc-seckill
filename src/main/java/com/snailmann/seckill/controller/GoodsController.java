package com.snailmann.seckill.controller;

import com.snailmann.seckill.entity.User;
import com.snailmann.seckill.entity.dto.GoodsDto;
import com.snailmann.seckill.redis.service.RedisHandler;
import com.snailmann.seckill.service.GoodsService;
import com.snailmann.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品goods控制层
 * 这里凡是出现了User参数，都是通过参数处理去处理的，前端仅仅是出传入token,参数处理器根据token
 * 在redis中获取用户信息，传入User信息
 * @author liwenjie
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    UserService userService;

    @Autowired
    RedisHandler redisHandler;

    @Autowired
    GoodsService goodsService;

    /**
     * 功能：登录成功后跳转到商品列表页面
     * 为了兼容多方式传递携带的token
     * <p>
     * 1. Cookie形式
     * 2. get请求参数形式
     * 3. header形式
     * <p>
     * 这里使用HandlerMethodArgumentResolver来实现拦截器的作用
     * 通过自定义参数处理器去获取token, 然后去redis获取user信息，传入参数user
     *
     * @param model
     * @return
     */
    @RequestMapping("/toList")
    public String toList(User user, Model model) {
        model.addAttribute("user", user);

        //查询商品列表
        List<GoodsDto> goodsDtoList = goodsService.listGoodsDto();
        model.addAttribute("goodsDtoList", goodsDtoList);

        return "goods-list";
    }

    /**
     * 在商品列表页面，点击详情，跳转到商品详情页面
     *
     * @return
     */
    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable String goodsId) {
        model.addAttribute("user", user);

        GoodsDto goodsDto = goodsService.getGoodsDto(goodsId);
        //用时间戳来比较时间
        long startTime = goodsDto.getStartDate().getTime();
        long endTime = goodsDto.getEndDate().getTime();
        long nowTime = System.currentTimeMillis();

        //秒杀状态 0 未开始 1 正在进行 2 已结束
        int seckillStatus = 0;
        //还剩下多少秒开始秒杀
        long remainSeconds = 0;

        //如果当前时间小于该商品的秒杀开始时间，则秒杀尚未开始
        if (nowTime < startTime) {
            seckillStatus = 0;
            remainSeconds = ((startTime - nowTime) / 1000);
        }
        //如果当前时间大于该商品的秒杀结束时间，则秒杀已经结束了
        else if (nowTime > endTime) {
            seckillStatus = 2;
            remainSeconds = -1;
        } else { //秒杀正则进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("seckillStatus",seckillStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("goodsDto",goodsDto);
        return "goods-detail";
    }

}
