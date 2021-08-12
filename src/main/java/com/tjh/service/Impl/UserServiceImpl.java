package com.tjh.service.Impl;

import com.tjh.entity.User;
import com.tjh.mapper.UserMapper;
import com.tjh.service.UserService;
import com.tjh.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created  on 2021/3/19
 * author: tjh
 * blog
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Transactional
    @Override
    public User checkUser(String username, String password) {
        User user = userMapper.findByUsernameAndPassword(username, MD5Utils.code(password));
        //System.out.println("user====================================>>>>"+user);
        return  user;
    }
}
