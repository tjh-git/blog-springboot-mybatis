package com.tjh.mapper;

import com.tjh.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author tjh
 * #Description UserMapper
 * #Date: 2021/3/25 15:11
 */
@Mapper
@Repository
public interface UserMapper {

    User findByUsernameAndPassword(String username,String password);

}
