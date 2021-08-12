package com.tjh.service.Impl;

import com.tjh.MyNotFoundException;
import com.tjh.entity.Blog;
import com.tjh.entity.BlogAndTag;
import com.tjh.entity.Comment;
import com.tjh.entity.Tag;
import com.tjh.mapper.BlogMapper;
import com.tjh.mapper.CommentMapper;
import com.tjh.service.BlogService;
import com.tjh.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author tjh
 * #Description BlogServiceImpl
 * #Date: 2021/3/25 21:59
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Blog getBlog(Long id) {
        return blogMapper.getBlog(id);
    }

    @Override
    public Blog getDetailedBlog(Long id) {
        System.out.println("id======"+id);
        Blog blog = blogMapper.getDetailedBlog(id);
        if (blog == null) {
            throw new MyNotFoundException("该博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));  //将Markdown格式转换成html
        blog.setViews(blog.getViews()+1);
        blogMapper.updateViews(blog);
        return blog;
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogMapper.getAllBlog();
    }

    @Override
    public List<Blog> getByTypeId(Long typeId) {
        return blogMapper.getByTypeId(typeId);
    }

    @Override
    public List<Blog> getByTagId(Long tagId) {
        return blogMapper.getByTagId(tagId);
    }

    @Override
    public List<Blog> getIndexBlog() {
        return blogMapper.getIndexBlog();
    }

    @Override
    public List<Blog> getAllRecommendBlog() {
        return blogMapper.getAllRecommendBlog();
    }

    @Override
    public List<Blog> getPortRecommendBlog() {
        List<Blog> tmpRecommendBlog = new ArrayList();
        List<Blog> allRecommendBlog = blogMapper.getAllRecommendBlog();
        for(int i = 0; i < 10; i++)
            tmpRecommendBlog.add(allRecommendBlog.get(i));
        return tmpRecommendBlog;
    }

    @Override
    public List<Blog> get3RecommendBlog() {
        List<Blog> tmpRecommendBlog = new ArrayList();
        List<Blog> allRecommendBlog = blogMapper.getAllRecommendBlog();
        if(allRecommendBlog.size() > 3) {
            for (int i = 0; i < 3; i++)
                tmpRecommendBlog.add(allRecommendBlog.get(i));
            return tmpRecommendBlog;
        }
        else
            return allRecommendBlog;
    }

    @Override
    public List<Blog> getSearchBlog(String query) {
        return blogMapper.getSearchBlog(query);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogMapper.findGroupYear();
        Set<String> set = new HashSet<>(years);  //set去掉重复的年份
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : set)
            map.put(year, blogMapper.findByYear(year));
        return map;
    }

    @Override
    public int countBlog() {
        return blogMapper.getAllBlog().size();
    }

    @Override
    public List<Blog> searchAllBlog(Blog blog) {
        return blogMapper.searchAllBlog(blog);
    }

    @Override
    public int saveBlogAndTag(BlogAndTag blogAndTag) {
        return blogMapper.saveBlogAndTag(blogAndTag);
    }

    @Override    //新增博客
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        //保存博客
        blogMapper.saveBlog(blog);
        //保存博客后才能获取自增的id
        Long id = blog.getId();
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        //维护blogAndTag
        for (Tag tag : tags) {

            //这里blog类中的指针相连，但是我们查tag是根据BlogAndTag查的，所以应该连接这个
            blogAndTag = new BlogAndTag(tag.getId(), id);
            blogMapper.saveBlogAndTag(blogAndTag);
        }
        return 1;
    }

    @Override   //编辑博客
    public int updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        //这里应该先删，再更新
        blogMapper.deleteBlogAndTag(blog.getId());
        //防止null值
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = tags.get(i);
            if(tag == null)
                continue;
            blogAndTag = new BlogAndTag(tag.getId(), blog.getId());
            blogMapper.saveBlogAndTag(blogAndTag);
        }
        return blogMapper.updateBlog(blog);
    }

    @Override
    public int deleteBlog(Long id) {
        List<Comment> replyComments = commentMapper.findByBlogId(id);
        for(Comment replyComment : replyComments) {
            commentMapper.deleteComment(replyComment);
        }
        blogMapper.deleteBlogAndTag(id);
        return blogMapper.deleteBlog(id);
    }

    @Override
    public int deleteBlogAndTag(Long id) {
        return blogMapper.deleteBlogAndTag(id);
    }
}
