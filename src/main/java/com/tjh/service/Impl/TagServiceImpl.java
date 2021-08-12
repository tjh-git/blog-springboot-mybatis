package com.tjh.service.Impl;

import com.tjh.MyNotFoundException;
import com.tjh.entity.Blog;
import com.tjh.entity.Tag;
import com.tjh.mapper.BlogMapper;
import com.tjh.mapper.TagMapper;
import com.tjh.service.TagService;
import com.tjh.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tjh
 * #Description TagServiceImpl
 * #Date: 2021/3/25 19:37
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogMapper blogMapper;



    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagMapper.saveTag(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagMapper.getTag(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Transactional
    @Override
    public List<Tag> getAllTag() {
        List<Tag> allTag = tagMapper.getAllTag();
        //for (Tag tag : allTag) {
        //    System.out.println("tag============tagServiceImpl"+tag);
        //}
        return allTag;
    }

    @Transactional
    @Override
    public List<Tag> getBlogTag() {
        return tagMapper.getBlogTag();
    }

    //@Transactional
    //@Override
    //public List<Tag> getTagByString(String text) {
    //    System.out.println("text qian :=================="+text);
    //    boolean flag = true;
    //    StringBuffer sb = new StringBuffer();
    //
    //    String[] tags = text.split(",");
    //    for(int i=0;i<tags.length;i++){
    //        if(flag){
    //            sb.append(tags[i]);
    //            flag= false;
    //        }else{
    //            sb.append(",");
    //            sb.append(tags[i]);
    //        }
    //        System.out.println("================tagServiecImpl getTagByString(text)=================");
    //        System.out.println(tags[i]);
    //        if (!StringUtils.isNumber(tags[i])){
    //            tagMapper.saveTag(new Tag(tags[i]));
    //            Tag tagByName = tagMapper.getTagByName(tags[i]);
    //            tags[i] = tagByName.getId().toString();
    //        }
    //    }
    //    return tagMapper.getBlogTag();
    //}

    @Override
    public List<Tag> getTagByString(String text) {    //从tagIds字符串中获取id，根据id获取tag集合
        List<Tag> tags = new ArrayList<>();
        List<Long> longs = convertToList(text);
        for (Long long1 : longs) {
            tags.add(tagMapper.getTag(long1));
        }
        return tags;
    }

    private List<Long> convertToList(String ids) {  //把前端的tagIds字符串转换为list集合
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }


    @Transactional
    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }

    @Transactional
    @Override
    public int deleteTag(Long id) {

        List<Blog> blogFromThisTag = blogMapper.getByTagId(id);
        //先检查这些blog是否只有这一个标签，是的话不允许删除
        for(Blog blog : blogFromThisTag) {
            List<Tag> oldTags = blogMapper.findSelfTags(blog);
            if(oldTags.size() == 1)
                throw new MyNotFoundException("还有博客使用此标签，不允许删除");
        }
        //先根据TagId删除对应的BlogAndTag
        tagMapper.deleteBlogAndTagFromTag(id);
        //将blog中对应的tags属性更新
        for(Blog blog : blogFromThisTag) {
            List<Tag> newTags = blogMapper.findSelfTags(blog);
            blog.setTags(newTags);
            blog.init();
        }

        return tagMapper.deleteTag(id);


        //return tagMapper.deleteTag(id);
    }
}
