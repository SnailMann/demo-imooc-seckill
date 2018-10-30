package com.snailmann.seckill.controller;

import com.snailmann.seckill.entity.ouput.CodeMsg;
import com.snailmann.seckill.entity.ouput.Result;
import com.snailmann.seckill.entity.vo.LoginVo;
import com.snailmann.seckill.service.UserService;
import com.snailmann.seckill.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public Result<Object> doLogin(LoginVo loginVo) {
        log.info("login: {}", loginVo.toString());

        String passInput = loginVo.getPassword();
        String mobile = loginVo.getMobile();

        //判断密码
        if (StringUtils.isEmpty(passInput))
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        //判断手机号不为空
        if (StringUtils.isBlank(mobile))
            return Result.error(CodeMsg.MOBILE_EMPTY);
        //判断手机格式是否正确
        if (!ValidatorUtil.isMobile(mobile))
            return Result.error(CodeMsg.MOBILE_ERROR);


        //登录
        CodeMsg code = userService.login(loginVo);
        if (code.getCode() == 0)
            return Result.success(CodeMsg.SUCCESS);
        else
            return Result.error(code);


    }
}
