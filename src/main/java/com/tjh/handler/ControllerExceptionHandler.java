package com.tjh.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局处理异常类
 */
//拦截controller注解的控制器
@ControllerAdvice
public class ControllerExceptionHandler {

    private final  Logger logger = LoggerFactory.getLogger(this.getClass());

    //异常处理，拦截所有的exception
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception e) throws Exception {

        logger.error("Request URL : {} , Exception : {}", request.getRequestURL(), e);

        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }

        ModelAndView model = new ModelAndView();
        model.addObject("url",request.getRequestURL());
        model.addObject("exception", e);

        //设置要跳转的视图
        model.setViewName("error/error");

        return model;
    }


}
