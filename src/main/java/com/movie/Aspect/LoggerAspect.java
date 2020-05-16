package com.movie.Aspect;


import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.MyLogger;
import com.movie.Enums.ExceptionEnums;
import com.movie.Enums.Role;
import com.movie.Plugins.SysLog;
import com.movie.Repository.MyLoggerRepository;
import com.movie.Util.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LoggerAspect {

    @Autowired
    private MyLoggerRepository myLoggerRepository;

    public final static Logger logger= LoggerFactory.getLogger(LoggerAspect.class);


    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.movie.Plugins.SysLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        System.out.println("切面。。。。。");
        //保存日志
        MyLogger sysLog = new MyLogger();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        SysLog myLog = method.getAnnotation(SysLog.class);
        if (myLog != null) {
            String value = myLog.value();
            sysLog.setOperation(value);//保存获取的操作
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        Object[] arguments  = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                continue;
            }
            arguments[i] = args[i];
        }
        String paramter = "";
        if (arguments != null) {
            try {
                paramter = JSONObject.toJSONString(arguments);
            } catch (Exception e) {
                paramter = arguments.toString();
            }
        }
        sysLog.setParams(paramter);

        sysLog.setCreateDate(new Date());
        //获取用户名
        //sysLog.setUsername(ShiroUtils.getUserEntity().getUsername());
        //获取用户ip地址

        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        sysLog.setIp(IPUtils.getIpAddr(request));

        String type=request.getHeader("type");
        if (type!=null){
            sysLog.setRole(Role.valueOf(type));
        }else {
            sysLog.setRole(Role.Vistor);
        }
        System.out.println(sysLog.getRole());

        Cookie[] cookies=request.getCookies();
       if(cookies.length>0){
           for (Cookie cookie : cookies) {
               if(cookie.getName().equals("id"))
                   sysLog.setUserId(cookie.getValue());
           }
       }
        //调用service保存SysLog实体类到数据库
        myLoggerRepository.save(sysLog);
    }
}
