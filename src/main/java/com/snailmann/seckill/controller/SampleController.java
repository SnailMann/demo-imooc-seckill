package com.snailmann.seckill.controller;


import com.snailmann.seckill.entity.ouput.Result;
import com.snailmann.seckill.entity.po.UserTest;
import com.snailmann.seckill.redis.service.RedisHandler;
import com.snailmann.seckill.redis.template.impl.UserKey;
import com.snailmann.seckill.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {


    private UserTestService userTestService;
    private final RedisHandler redisHandler;

    @Autowired
    public SampleController(UserTestService userTestService, RedisHandler redisHandler) {
        this.userTestService = userTestService;
        this.redisHandler = redisHandler;
    }


    @GetMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Joshua");
        return "hello";
    }

    @GetMapping("/db/get")
    public Result<UserTest> dbGet() {
        UserTest user = userTestService.getById(1);
        return Result.success(user);
    }


    @GetMapping("/db/tx")
    public Result<Boolean> dbTx() {
        return Result.success(userTestService.tx());
    }

    @GetMapping("/redis/get")
    public Result<Object> redisGet() {
        return Result.success(redisHandler.get(UserKey.getById,"1", Long.class));
    }


    @GetMapping("/redis/set")
    public Result<Object> redisSet() {
        redisHandler.set(UserKey.getById,"2",567);
        return Result.success(redisHandler.get(UserKey.getById,"2",String.class));
    }


}
