package com.tjh.service.Impl;

import com.tjh.entity.Type;
import com.tjh.mapper.TypeMapper;
import com.tjh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tjh
 * #Description 文章分类业务层接口实现类
 * #Date: 2021/3/25 10:53
 */
@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public int saveType(Type type) {
        return typeMapper.saveType(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeMapper.getType(id);
    }

    @Transactional
    @Override
    public List<Type> getAllType() {
        return typeMapper.getAllType();
    }

    @Transactional
    @Override
    public List<Type> getAllTypeAndBlog() {
        return typeMapper.getAllTypeAndBlog();
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Transactional
    @Override
    public int updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeMapper.deleteType(id);
    }

    @Override
    public List<Type> getBlogType() {
        return typeMapper.getBlogType();
    }
}
