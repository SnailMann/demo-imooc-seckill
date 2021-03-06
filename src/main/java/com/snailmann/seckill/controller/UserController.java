package com.snailmann.seckill.controller;

import com.snailmann.seckill.common.Result;
import com.snailmann.seckill.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 返回用户信息（压测）
     * @param model
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/ansy/info")
    public Callable<Result<User>> ansyInfo(Model model,User user){
        log.info("info : {}",user.getNickName());
        Callable<Result<User>> result = () -> {
            doSomething();
            return Result.success(user);
        };
        return result;
    }


    /**
     * 返回用户信息（压测）
     * @param model
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/info")
    public Result<User> info(Model model,User user){
        log.info("info : {}",user.getNickName());
        doSomething();
        return Result.success(user);
    }

    public void doSomething(){
        log.info(Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
