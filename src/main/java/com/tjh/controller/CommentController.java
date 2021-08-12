package com.tjh.controller;

import com.tjh.entity.Blog;
import com.tjh.entity.Comment;
import com.tjh.entity.User;
import com.tjh.service.BlogService;
import com.tjh.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")  //展示留言
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.getCommentByBlogId(blogId));
        model.addAttribute("blog", blogService.getDetailedBlog(blogId));
        return "blog :: commentList";
        //return "blog";
        //return "redirect:/blog/" + blogId;
    }


    @PostMapping("/comments")   //提交留言
    public String post(Comment comment, HttpSession session, RedirectAttributes attributes){
        //后端校验
        if(comment.getContent()=="" || comment.getEmail()=="" || comment.getNickname()=="") {
            attributes.addFlashAttribute("msg", "请填写完整评论信息");
            System.out.println("提交留言======================");
            return "redirect:/comments/" + comment.getBlogId();
        }
        Long blogId = comment.getBlogId();



        comment.setBlog(blogService.getDetailedBlog(blogId));  //绑定博客与评论
        comment.setBlogId(blogId);
        User user = (User) session.getAttribute("user");
        comment.setAvatar(avatar);
        if (user != null) {   //用户为管理员
            if (comment.getNickname().equals(user.getNickname()) && comment.getEmail().equals(user.getEmail())) {
                comment.setAdminComment(true);
                comment.setAvatar(user.getAvatar());
            }
        }
        commentService.saveComment(comment);
        //return "redirect:/comments/" + blogId;
        return "redirect:/blog/" + blogId;
    }

    @GetMapping("/comment/{blogId}/{id}/delete")    //删除评论
    public String delete(@PathVariable Long blogId, @PathVariable Long id, Comment comment, RedirectAttributes attributes, Model model){
        commentService.deleteComment(comment);
        Blog detailedBlog = blogService.getDetailedBlog(blogId);
        List<Comment> comments = commentService.getCommentByBlogId(blogId);
        model.addAttribute("blog", detailedBlog);
        model.addAttribute("comments", comments);
        return "blog";
    }

}