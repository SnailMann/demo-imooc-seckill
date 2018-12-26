package com.snailmann.seckill.controller;

import com.snailmann.seckill.entity.template.Result;
import com.snailmann.seckill.entity.in.LoginParam;
import com.snailmann.seckill.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@Slf4j
@Controller

public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 显示登录界面，初始界面
     *
     * @return
     */
    @GetMapping("/login/toLogin")
    public String toLogin() {
        System.out.println("toLogin...");
        return "login";
    }


    /**
     * 前端点击登录，触发登录操作
     *
     * 1. 这里有个坑，可以看见参数中没有@ResponseBody参数，所以当我们使用postman模拟请求的时候，使用json传递是无法执行的
     * 但是如果加了@ResponseBody,使用这里的页面登录也是会报错的...所以这里存在一个不兼容的问题
     *
     * @param response
     * @param loginVo
     * @return
     */
    @PostMapping("login/doLogin")
    @ResponseBody
    public Result<Object> doLogin(HttpServletResponse response,  @Validated @RequestBody LoginParam loginVo) {
        log.info("login: {}", loginVo.toString());
        //登录
        userService.login(response, loginVo);
        return Result.success(true);
    }


}
