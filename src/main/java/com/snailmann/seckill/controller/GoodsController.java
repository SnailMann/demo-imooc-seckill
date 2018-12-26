package com.snailmann.seckill.controller;

import com.snailmann.seckill.entity.User;
import com.snailmann.seckill.redis.service.RedisHandler;
import com.snailmann.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 商品goods控制层
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    UserService userService;

    @Autowired
    RedisHandler redisHandler;

    /**
     * 为了兼容多方式传递携带的token
     *
     * 1. Cookie形式
     * 2. get请求参数形式
     * 3. header形式
     *
     * 这里使用HandlerMethodArgumentResolver来实现拦截器的作用
     * 通过自定义参数处理器去获取token, 然后去redis获取user信息，传入参数user
     *
     * @param model
     * @return
     */
    @RequestMapping("/to_list")
    public String toList(User user,Model model) {
        model.addAttribute("user", user);
        return "goods_list";
    }


}