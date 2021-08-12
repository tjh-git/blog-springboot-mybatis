package com.tjh.service;

import com.tjh.entity.Comment;

import java.util.List;

/**
 * @author tjh
 * #Description CommentService
 * #Date: 2021/3/26 10:27
 */
public interface CommentService {

    List<Comment> getCommentByBlogId(Long blogId);

    List<Comment> findSecondaryCommentBySelfId(Long id);

    int saveComment(Comment comment);

    int deleteComment(Comment comment);
}
