package com.tjh.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 分类实体类
 * Created  on 2021/3/24
 * author: tjh
 * blog
 */
public class Type {

    private Long id;
    private String name;

    private List<Blog> blogs = new ArrayList<>();

    public Type() {
    }

    public Type(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}