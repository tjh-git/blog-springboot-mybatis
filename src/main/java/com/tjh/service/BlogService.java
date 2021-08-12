package com.tjh.service;

import com.tjh.entity.Blog;
import com.tjh.entity.BlogAndTag;
import com.tjh.entity.Tag;
import com.tjh.queryvo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author tjh
 * #Description BlogService
 * #Date: 2021/3/25 21:59
 */

public interface BlogService {

    Blog getBlog(Long id);  //后台展示博客

    Blog getDetailedBlog(Long id);  //前端展示博客

    List<Blog> getAllBlog();

    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客

    List<Blog> getByTagId(Long tagId);  //根据标签id获取博客

    List<Blog> getIndexBlog();  //主页博客展示

    List<Blog> getAllRecommendBlog();  //推荐博客展示

    List<Blog> getPortRecommendBlog();  //推荐博客展示

    List<Blog> get3RecommendBlog();  //推荐博客展示

    List<Blog> getSearchBlog(String query);  //全局搜索博客

    Map<String, List<Blog>> archiveBlog();  //归档博客

    int countBlog();  //查询博客条数

    int saveBlog(Blog blog);

    int updateBlog(Blog blog);

    int deleteBlog(Long id);

    int deleteBlogAndTag(Long id);

    List<Blog> searchAllBlog(Blog blog);

    int saveBlogAndTag(BlogAndTag blogAndTag);  //down

}
