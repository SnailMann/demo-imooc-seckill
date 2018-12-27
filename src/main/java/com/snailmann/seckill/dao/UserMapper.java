package com.snailmann.seckill.dao;

import com.snailmann.seckill.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户Dao层
 * @author liwenjie
 */
@Mapper
public interface UserMapper {

    @Select("select * from seckill_user where id = #{id}")
    User getById(@Param("id") long id);
}
