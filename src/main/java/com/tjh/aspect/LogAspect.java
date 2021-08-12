package com.tjh.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

//切面
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 定义切面
     *   *.*(..) 任何类的所有方法都拦截
     */
    @Pointcut("execution(* com.tjh.controller.*.*(..))")
    public void log(){

    }


    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request  = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()
                +"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        ReqeustLog reqeustLog = new ReqeustLog(url, ip, classMethod, args);

        logger.info("Request : {}",reqeustLog);

        //logger.info("----------------deBefore-------------------");
    }

    @After("log()")
    public void doAfter(){
        //logger.info("----------------doAfter-------------------");
    }

    //拦截后返回的
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Return ------ {}",result );
    }


    private class ReqeustLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public ReqeustLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }


}
