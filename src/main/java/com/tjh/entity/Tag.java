package com.tjh.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tjh
 * #Description Tag
 * #Date: 2021/3/25 19:34
 */
public class Tag {
    private Long id;
    private String name;

    private List<Blog> blogs = new ArrayList<>();


    public Tag() {
    }

    public Tag(String name){
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
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}
