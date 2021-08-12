package com.tjh.mapper;

import com.tjh.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tjh
 * #Description CommentMapper
 * #Date: 2021/3/26 10:26
 */
@Mapper
@Repository
public interface CommentMapper {

    //根据创建时间倒序来排
    List<Comment> findByBlogIdAndParentCommentNull(@Param("blogId") Long blogId, @Param("blogParentId") Long blogParentId);

    //搜寻此blog下的所有comment
    List<Comment> findByBlogId(@Param("blogId") Long blogId);

    //根据父级Id查询父级对象，暂时没用到
    Comment findByParentCommentId(@Param("parentCommentId")Long parentcommentid);

    //查询我自己
    Comment findById(@Param("Id")Long id);

    //添加一个评论
    int saveComment(Comment comment);

    //删除评论
    int deleteComment(Comment comment);

    //根据父级Id查询对象子对象
    List<Comment> findSecondaryCommentBySelfId(@Param("commentId") Long commentId);

}
