package com.snailmann.seckill.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    /**
     * 用户id
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 头像
     */
    private String head;
    /**
     * 注册时间
     */
    private Date registerDate;
    /**
     * 上次登录时间
     */
    private Date lastLoginDate;
    /**
     * 登录次数
     */
    private Integer loginCount;

}
