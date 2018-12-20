package com.snailmann.seckill.controller;

import com.snailmann.seckill.entity.po.User;
import com.snailmann.seckill.redis.service.RedisHandler;
import com.snailmann.seckill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
     * 1. Cookie形式
     * 2. get请求参数形式
     * 3. header形式
     *
     * @param cookieToken
     * @param paramToken
     * @param headerToken
     * @param model
     * @return
     */
    @RequestMapping("/to_list")
    public String toLogin(@CookieValue(value = "token", required = false) String cookieToken,
                          @RequestParam(value = "token", required = false) String paramToken,
                          @RequestHeader(value = "token", required = false) String headerToken,
                          HttpServletResponse response,
                          Model model) {

        //如果所有形式的传递都没有token，返回登录页面
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(headerToken) && StringUtils.isEmpty(paramToken)) {
            return "login";
        }

        //按照优先级顺序，如果param空，则取header，如果header也为空，则取cookie
        String token = StringUtils.isEmpty(paramToken)
                ? StringUtils.isEmpty(headerToken) ? cookieToken : headerToken
                : paramToken;


        User user = userService.getByToken(response, token);


        model.addAttribute("user", user);
        return "goods_list";
    }


}
