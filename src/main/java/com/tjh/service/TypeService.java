package com.tjh.service;

import com.tjh.entity.Type;

import java.util.List;

/**
 * @Description: 文章分类业务层接口
 * Created  on 2021/3/25
 * author: tjh
 * blog
 */

public interface TypeService {

    int saveType(Type type);

    Type getType(Long id);

    List<Type> getAllType();

    List<Type>getAllTypeAndBlog();

    Type getTypeByName(String name);

    int updateType(Type type);

    void deleteType(Long id);

    List<Type> getBlogType();  //首页右侧展示type对应的博客数量


}
