package com.tjh.mapper;

import com.tjh.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created  on 2021/3/24
 * author: tjh
 * blog
 */
@Mapper
@Repository
public interface TypeMapper {

    int saveType(Type type);

    Type getType(Long id);

    List<Type> getAllType();

    List<Type>getAllTypeAndBlog();

    Type getTypeByName(String name);

    int updateType(Type type);

    void deleteType(Long id);

    List<Type> getBlogType();  //首页右侧展示type对应的博客数量

}
