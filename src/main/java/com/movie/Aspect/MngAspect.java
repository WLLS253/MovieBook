package com.movie.Aspect;


import com.movie.Enums.ExceptionEnums;
import com.movie.Exception.AuthorException;
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
import java.io.PrintStream;

@Aspect
@Component
public class MngAspect {

    public  final static Logger logger= LoggerFactory.getLogger(MngAspect.class);

    @Pointcut("execution(public * com.movie.Controller.RouterController.getMngPage(..))")
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

        if(type==null||(!type.equals("CinemaMng"))){
            throw new AuthorException(ExceptionEnums.AUTHOR_EEOR_Mng);
        }
    }






}
