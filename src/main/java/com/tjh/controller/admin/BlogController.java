package com.tjh.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tjh.entity.*;
import com.tjh.queryvo.BlogQuery;
import com.tjh.queryvo.SearchBlog;
import com.tjh.queryvo.ShowBlog;
import com.tjh.service.BlogService;
import com.tjh.service.TagService;
import com.tjh.service.TypeService;
import com.tjh.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tjh
 * #Description BlogController
 * #Date: 2021/3/25 21:39
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    public void setTypeAndTag(Model model) {    //在前端的下拉框里赋值
        model.addAttribute("types", typeService.getAllType());

        model.addAttribute("tags", tagService.getAllTag());
    }

    @GetMapping("/blogs")           //Model是thymeleaf的内容，是前端的页面展示模型，并用来传值。这一段是分页处理。
    public String blogs(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 5);
        List<Blog> allBlog = blogService.getAllBlog();
        //得到分页结果对象
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlog);
        model.addAttribute("page", pageInfo);
        setTypeAndTag(model);  //查询类型和标签
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")  //按条件查询博客
    public String searchBlogs(Blog blog, @RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 5);
        List<Blog> allBlog = blogService.searchAllBlog(blog);
        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("page", pageInfo);
        model.addAttribute("message", "查询成功");
        setTypeAndTag(model);
        return "admin/blogs :: blogList";
        //return "admin/blogs";
    }

    @GetMapping("/blogs/input") //去新增博客页面
    public String toAddBlog(Model model) {
        model.addAttribute("blog", new Blog());
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/input")
    public String toEditBlog(@PathVariable Long id, Model model) {
        Blog blog = blogService.getBlog(id);
        blog.init();   //将tags集合转换为tagIds字符串
        //System.out.println("blog==========================================="+blog);
        model.addAttribute("blog", blog);     //返回一个blog对象给前端th:object
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    @PostMapping("/blogs")
    public String addBlog(Blog blog, HttpSession session, RedirectAttributes attributes) {

        //无论是编辑还是新建，都会要重置所有属性值，就算是相同的也应该要重置
        blog.setUser((User) session.getAttribute("user"));
        //设置用户id
        blog.setUserId(blog.getUser().getId());
        //设置用户views
        blog.setViews(1);
        //设置blog的type
        blog.setType(typeService.getType(blog.getType().getId()));


        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //给blog中的List<Tag>赋值
        String tagIds = blog.getTagIds();//修改  “1,2，标签，3”为 “1,2,3,4”
        //修改  “1,2，标签，3”为 “1,2,3,4”
        blog.setTagIds(xiugai(tagIds, blog));
        blog.setTags(tagService.getTagByString(blog.getTagIds()));

        blogService.saveBlog(blog);
        attributes.addFlashAttribute("msg", "添加成功");
        return "redirect:/admin/blogs";
    }

    private String xiugai(String ids, Blog blog) {
        StringBuffer sb = new StringBuffer();
        //List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            boolean flag = true;
            for (int i = 0; i < idarray.length; i++) {
                if (!StringUtils.isNumber(idarray[i])) {
                    tagService.saveTag(new Tag(idarray[i]));
                    Tag tagByName = tagService.getTagByName(idarray[i]);
                    //System.out.println(tagByName.getId());

                    //System.out.println(tagByName.getId() + "blogController" + blog.getId());
                    //推荐用的标签，标签分类用的
                    BlogAndTag blogAndTag = new BlogAndTag(tagByName.getId(), blog.getId());
                    blogService.saveBlogAndTag(blogAndTag);

                    idarray[i] = tagByName.getId().toString();
                    if (flag) {
                        sb.append(tagByName.getId().toString());
                        flag = false;
                    } else {
                        sb.append(",");
                        sb.append(tagByName.getId().toString());
                    }
                } else {
                    if (flag) {
                        sb.append(idarray[i]);
                        flag = false;
                    } else {
                        sb.append(",");
                        sb.append(idarray[i]);
                    }
                }
            }
        }
        return sb.toString();
    }


    @PostMapping("/blogs/{id}")
    public String updateBlog(Blog blog, HttpSession session, RedirectAttributes attributes) {
        //无论是编辑还是新建，都会要重置所有属性值，就算是相同的也应该要重置
        blog.setUser((User) session.getAttribute("user"));
        //设置用户id
        blog.setUserId(blog.getUser().getId());
        //设置用户views
        blog.setViews(blog.getViews());
        //设置blog的type
        blog.setType(typeService.getType(blog.getType().getId()));


        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //给blog中的List<Tag>赋值

        String tagIds = blog.getTagIds();//修改  “1,2，标签，3”为 “1,2,3,4”
        //修改  “1,2，标签，3”为 “1,2,3,4”
        blog.setTagIds(xiugai(tagIds, blog));

        blog.setTags(tagService.getTagByString(blog.getTagIds()));

        blogService.updateBlog(blog);
        attributes.addFlashAttribute("msg", "更新成功");
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/blogs";
    }

}
