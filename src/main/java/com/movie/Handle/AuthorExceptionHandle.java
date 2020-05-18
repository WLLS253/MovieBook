//package com.movie.Handle;
//
//
//import com.movie.Exception.AuthorException;
//import com.movie.Exception.LoginException;
//import com.movie.Result.Result;
//import com.movie.Util.Util;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@ControllerAdvice
//public class AuthorExceptionHandle {
//
//    private final static Logger logger = LoggerFactory.getLogger(Exception.class);
//
//    @ExceptionHandler(value = Exception.class)
//    public String PageHandle(Exception e){
//
//        if(e instanceof AuthorException){
//            AuthorException authorException=(AuthorException)e;
//            return Util.failure(authorException.getCode(), authorException.getMessage());
//        }else {
//            logger.info("系统异常{}",e);
//            //return Util.failure(-1,"未知错误");
//        }
//
//
//
//    }
//
//}
