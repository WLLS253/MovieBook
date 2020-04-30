package com.movie.Util;


import com.movie.Enums.ExceptionEnums;
import com.movie.Result.Result;

public class Util {


    public  static Result success(Object object){


        Result result =new Result();

        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return  result;
    }

    public  static  Result success(){

        Result result=new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(null);
        return result;
    }

    public static Result success(ExceptionEnums exceptionEnums){
        Result result=new Result();
        result.setCode(exceptionEnums.getCode());
        result.setMsg(exceptionEnums.getMsg());
        result.setData(null);

        return  result;
    }


    public   static  Result failure(Integer code,String msg){

        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);

        return  result;

    }
    public   static  Result failure(ExceptionEnums exceptionEnums){

        Result result=new Result();
        result.setCode(exceptionEnums.getCode());
        result.setMsg(exceptionEnums.getMsg());
        result.setData(null);

        return  result;

    }

}



