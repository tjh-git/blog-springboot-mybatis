package com.tjh.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tjh.entity.Type;
import com.tjh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Created  on 2021/3/25
 * author: tjh
 * blog
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //    分页查询分类列表
    //@PageableDefault(size=10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable
    //每页10个数据，通过id 倒序排序   id对应 pageNum 页数
    @GetMapping("/types")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum, Model model) {
        //按照排序字段 倒序 排序
        String orderBy = "id desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<Type> list = typeService.getAllType();
        PageInfo<Type> page = new PageInfo<>(list);
        model.addAttribute("page",page );
        return "admin/types";
    }

    // 返回新增分类页面
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * 新增分类
     *
     * @param type
     * @return
     */
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        //校验是否有相同的名称
        if (type1 != null) {
            //返回一个错误
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        int t = typeService.saveType(type);
        if (t == 0) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 去到更新分类页面，把id传过去
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * 更新分类
     *
     * @param type
     * @param result
     * @param id
     * @param attributes 传数据给前端
     * @return
     */
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        //校验是否有相同的名称
        if (type1 != null) {
            //返回一个错误
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        int t = typeService.updateType(type);
        if (t == 0) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    //删除分类
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        //下面默认走get请求
        return "redirect:/admin/types";
    }



    //@GetMapping("/test")
    //public String test(){
    //    Type type = typeMapper.getTypeByName("分类测试");
    //    System.out.println(type);
    //
    //    return "ok";
    //}
}
