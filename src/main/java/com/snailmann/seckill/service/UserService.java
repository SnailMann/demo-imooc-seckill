package com.snailmann.seckill.service;


import com.snailmann.seckill.dao.UserMapper;
import com.snailmann.seckill.entity.ouput.CodeMsg;
import com.snailmann.seckill.entity.po.User;
import com.snailmann.seckill.entity.vo.LoginVo;
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
        //延长有效期
        User user = redisHandler.get(UserKey.token, token, User.class);

        //重新生成（cookie）token，作用就是更新有效期时间
        if (user != null){
            createToken(response, user);
        }

        return user;

    }


    /**
     * 登录,判断是否允许登录
     *
     * @param loginVo  传入的登录信息
     * @param response 为返回Cookie
     * @return 登录成功返回true ,登录失败返回false
     */
    public boolean login(HttpServletResponse response, LoginVo loginVo) {


        /**
         * 1. 如果没有数据则为服务器错误
         */
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();

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
        createToken(response, user);


        return true;

    }

    /**
     * 实现分布式Session
     * 生成token,配置cookie
     * 以token为键，用户信息为值存放远程缓存中
     *
     * @param response
     * @param user
     */
    private void createToken(HttpServletResponse response, User user) {
        String token = UUIDUtils.createToken();
        UserKey userKey = UserKey.token;
        redisHandler.set(userKey, token, user);
        //配置Cookie
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(userKey.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
