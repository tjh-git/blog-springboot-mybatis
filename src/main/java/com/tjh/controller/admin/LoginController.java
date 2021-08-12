package com.tjh.controller.admin;

import com.tjh.entity.User;
import com.tjh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created  on 2021/3/19
 * author: tjh
 * blog
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;


    //不写默认上面的全局
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    //登录
    @PostMapping("/login")
     public String login(@RequestParam String username,
                         @RequestParam String password,
                         HttpSession session,
                         RedirectAttributes attributes){
        User user = userService.checkUser(username, password);

        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            //System.out.println("user========================>"+user);
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/admin";
        }


    }


    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
