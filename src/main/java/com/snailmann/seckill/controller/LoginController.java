package com.snailmann.seckill.controller;

import com.snailmann.seckill.entity.ouput.Result;
import com.snailmann.seckill.entity.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class LoginController {


    @GetMapping("/to_login")
    public String toLogin() {
        System.out.println("toLogin...");
        return "login";
    }


    @PostMapping("/login/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(LoginVo loginVo) {
        log.info("login: {}" , loginVo.toString());
        return Result.success(true);
    }
}
