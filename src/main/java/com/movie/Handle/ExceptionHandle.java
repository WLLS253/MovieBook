package com.movie.Handle;


import com.movie.Exception.AuthorException;
import com.movie.Exception.LoginException;
import com.movie.Result.Result;
import com.movie.Serivce.URLService;
import com.movie.Util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {


    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = AuthorException.class)
    public String Exceptionhand(AuthorException e){
      AuthorException authorException=e;
      logger.info("权限异常{}" ,authorException.getCode());
      switch (authorException.getCode()){
          case 16:
              return "mng/xdq_cinema_login";
          case 17:
               return "sys/index";
          case 15:
              return "index";
          default:
              return "index";
      }
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result Exceptionhand(Exception e){
        if(e instanceof LoginException){
            LoginException loginException =(LoginException) e;
            return Util.failure(loginException.getCode(), loginException.getMessage());
        }else {
            logger.info("系统异常{}",e);
            return Util.failure(-1,"未知错误");
        }
    }


}
