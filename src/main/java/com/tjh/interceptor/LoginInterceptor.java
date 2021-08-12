package com.tjh.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created  on 2021/3/19
 * author: tjh
 * blog
 */

//拦截器
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //没登录
        if(request.getSession().getAttribute("user")==null){
            response.sendRedirect("/admin");
            return false;
       }
        //System.out.println("登录了=======================================");
        return true;
    }
}
