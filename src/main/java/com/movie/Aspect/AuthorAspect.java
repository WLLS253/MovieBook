package com.movie.Aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthorAspect {

    public  final static Logger logger= LoggerFactory.getLogger(AuthorAspect.class);

    @Pointcut("execution(public * com.movie.Controller.RouterController.*(..))")
    public  void logMng(){
    }

    @Before("logMng()")
    public  void doBefore(JoinPoint joinPoint){

        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =attributes.getRequest();

        Cookie[] cookies = request.getCookies();
        String type=request.getHeader("type");


//        if (type==null||type.equals("User")||type.equals()){
//            throw new
//        }
    }






}
