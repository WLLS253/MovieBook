package com.movie.Aspect;


//import com.movie.Serivce.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {

//
//    @Autowired
//    private TokenService tokenService;




    public  final static Logger logger= LoggerFactory.getLogger(HttpAspect.class);
    @Before("execution(public * com.movie.Controller.testController.*(..))")
    public  void log(){
       logger.info("hhhh");
    }


    @Pointcut("execution(public * com.movie.Controller.testController.*(..))")
    public  void logtest(){

    }


    @Before("logtest()")
    public  void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         HttpServletRequest request =attributes.getRequest();

         logger.info("URL={}",request.getRequestURI());


         logger.info("method={}",request.getMethod());


         logger.info("IP={}",request.getRemoteAddr());

         logger.info("class_medthod={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());


         logger.info("args={}",joinPoint.getArgs());

        logger.info("dobefore");
        String url=request.getRequestURI();

//        String session_key=request.getHeader("session_key");
//        String id=request.getHeader("id");

//        if(!(url.equals("/t/login")||url.equals("/t/user/add"))) {
//            String token=request.getHeader("token");
//            logger.info("token={}",token);
//            if(token!=null){
//                String uuid=tokenService.validateToken(token,24*36000L);
//                if(uuid==null)throw new LoginException(ExceptionEnums.LOGIN_ERROR);
//            }
//        }
    }


    @After("logtest()")
    public  void doAfter(){

        logger.info("doafter");
    }


    @AfterReturning(returning = "object",pointcut = "logtest()")
    public void doAfterReturn(Object object){

//        logger.info("response={}",object.toString());
    }

}


