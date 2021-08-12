package com.tjh.mapper;

import com.tjh.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tjh
 * #Description TagMapper
 * #Date: 2021/3/25 19:35
 */
@Mapper
@Repository
public interface TagMapper {

    int saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    List<Tag> getAllTag();

    List<Tag> getBlogTag();  //首页右侧展示tag对应的博客数量

    List<Tag> getTagByString(String text);   //从字符串中获取tag集合

    int updateTag(Tag tag);

    int deleteTag(Long id);

    int deleteBlogAndTagFromTag(Long id);

}
