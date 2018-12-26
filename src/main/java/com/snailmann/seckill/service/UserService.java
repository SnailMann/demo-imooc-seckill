package com.snailmann.seckill.service;


import com.snailmann.seckill.dao.UserMapper;
import com.snailmann.seckill.entity.template.CodeMsg;
import com.snailmann.seckill.entity.User;
import com.snailmann.seckill.entity.in.LoginParam;
import com.snailmann.seckill.execption.GlobalException;
import com.snailmann.seckill.redis.service.RedisHandler;
import com.snailmann.seckill.redis.template.impl.UserKey;
import com.snailmann.seckill.util.MD5Utils;
import com.snailmann.seckill.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Service
public class UserService {


    private static String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    RedisHandler redisHandler;


    public User getById(Long id) {
        return userMapper.getById(id);
    }

    /**
     * 根据token获取当前登录的用户
     *
     * @param token
     * @return
     */
    public User getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        //根据token获取Session信息，里面存放有用户信息
        User user = redisHandler.get(UserKey.token, token, User.class);

        //每次执行该方法，意味着有用户请求，所以自动更新token的过期时间
        //更新有效期时间，即延长时间
        if (user != null) {
            addCookie(response, token, user);
        }

        return user;

    }


    /**
     * 登录,判断是否允许登录
     *
     * @param loginParam 传入的登录信息
     * @param response   为返回Cookie
     * @return 登录成功返回true ,登录失败返回false
     */
    public boolean login(HttpServletResponse response, LoginParam loginParam) {


        /**
         * 1. 如果没有数据则为服务器错误
         */
        if (loginParam == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginParam.getMobile();
        String formPass = loginParam.getPassword();

        /**
         * 2. 判断手机号是否存在
         */
        User user = userMapper.getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        /**
         * 3. 验证密码
         */
        String dbPass = user.getPassword();
        //从数据库取得密码和盐
        String dbSalt = user.getSalt();
        //将前端传入的第一层加密的formPass跟db取得的随机盐进行加密
        String calcPass = MD5Utils.formPassToDBPass(formPass, dbSalt);
        //判断客户端传入的密码加密后是否等于db中的密码
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        /**
         * 4. 生成Cookie
         */
        String token = UUIDUtils.createToken();
        //添加到Cookie中，并将session缓存到redis中
        addCookie(response, token, user);


        return true;

    }

    /**
     * 实现分布式Session
     * 生成token,配置cookie，将session信息放入redis缓存中
     * 以token为键，用户信息为值存放远程缓存中
     *
     * @param response
     * @param user
     * @param token    沿用传入的token
     */
    private void addCookie(HttpServletResponse response, String token, User user) {

        UserKey userKey = UserKey.token;
        //缓存到redis中
        redisHandler.set(userKey, token, user);
        //配置Cookie
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(userKey.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
