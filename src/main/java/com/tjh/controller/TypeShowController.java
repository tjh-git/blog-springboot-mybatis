package com.tjh.controller;

import com.tjh.entity.Blog;
import com.tjh.entity.Type;
import com.tjh.service.BlogService;
import com.tjh.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, @RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum,
                        Model model){
        PageHelper.startPage(pageNum, 10);  //开启分页
        List<Type> types = typeService.getBlogType();
        System.out.println("types:"+types);
        for (Type type : types) {
            System.out.println(type);
        }

        //-1从导航点过来的
        if (id == -1){
            if(types.size()!=0){
                id = types.get(0).getId();  //默认为列表第一个
            }
        }
        List<Blog> blogs = blogService.getByTypeId(id);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("types", types);
        model.addAttribute("page", pageInfo);
        model.addAttribute("activeTypeId", id);

        return "types";
    }
}
