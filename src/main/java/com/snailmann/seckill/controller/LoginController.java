package com.snailmann.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snailmann.seckill.entity.ouput.Result;
import com.snailmann.seckill.entity.vo.LoginVo;
import com.snailmann.seckill.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/to_login")
    public String toLogin() {
        System.out.println("toLogin...");
        return "login";
    }


    @PostMapping("/login/do_login")
    @ResponseBody
    public Result<Object> doLogin(@Validated LoginVo loginVo) {
        log.info("login: {}", loginVo.toString());
        //登录
        userService.login(loginVo);
        return Result.success(true);
    }



}
