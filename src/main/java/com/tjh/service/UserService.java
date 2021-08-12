package com.tjh.service;

import com.tjh.entity.User;

/**
 * Created  on 2021/3/19
 * author:
 * blog
 */
public interface UserService {

    User checkUser(String username, String password);

}
