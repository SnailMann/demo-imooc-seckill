package com.snailmann.seckill.service;


import com.snailmann.seckill.dao.UserMapper;
import com.snailmann.seckill.entity.ouput.CodeMsg;
import com.snailmann.seckill.entity.po.User;
import com.snailmann.seckill.entity.vo.LoginVo;
import com.snailmann.seckill.execption.GlobalException;
import com.snailmann.seckill.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public User getById(Long id){
        return userMapper.getById(id);
    }


    public boolean login(LoginVo loginVo) {

        //如果没有数据则为服务器错误
        if (loginVo == null)
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();

        //判断手机号是否存在
        User user = userMapper.getById(Long.parseLong(mobile));
        if (user == null)
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);

        //验证密码
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();      //从数据库取得密码和盐
        String calcPass = MD5Util.formPassToDBPass(formPass,dbSalt);//将前端传入的第一层加密的formPass跟db取得的随机盐进行加密
        if (!calcPass.equals(dbPass))   //判断客户端传入的密码加密后是否等于db中的密码
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);

        return true;

    }
}
