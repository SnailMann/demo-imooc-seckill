package com.snailmann.seckill.service;

import com.snailmann.seckill.dao.UserTestMapper;
import com.snailmann.seckill.entity.po.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserTestService {

    private UserTestMapper userTestMapper;

    @Autowired
    public UserTestService(UserTestMapper userTestMapper) {
        this.userTestMapper = userTestMapper;
    }

    public UserTest getById(int id){
        return userTestMapper.getById(id);
    }

    public void insertName(UserTest user){
        userTestMapper.insert(user);
    }

    @Transactional
    public boolean tx(){
        UserTest u1 = new UserTest();
        u1.setId(2);
        u1.setName("Tom");
        userTestMapper.insert(u1);

        UserTest u2 = new UserTest();
        u2.setId(1);
        u2.setName("Shame");
        userTestMapper.insert(u2);

        return true;
    }


}
