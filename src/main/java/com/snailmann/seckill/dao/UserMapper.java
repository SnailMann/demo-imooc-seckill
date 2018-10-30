package com.snailmann.seckill.dao;

import com.snailmann.seckill.entity.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from seckill_user where id = #{id}")
    User getById(@Param("id") long id);
}
