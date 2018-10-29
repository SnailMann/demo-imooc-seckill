package com.snailmann.seckill.service;

import com.snailmann.seckill.dao.UserMapper;
import com.snailmann.seckill.entity.po.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserTest getById(int id){
        return userMapper.getById(id);
    }

    public void insertName(UserTest user){
        userMapper.insert(user);
    }

    @Transactional
    public boolean tx(){
        UserTest u1 = new UserTest();
        u1.setId(2);
        u1.setName("Tom");
        userMapper.insert(u1);

        UserTest u2 = new UserTest();
        u2.setId(1);
        u2.setName("Shame");
        userMapper.insert(u2);

        return true;
    }


}
