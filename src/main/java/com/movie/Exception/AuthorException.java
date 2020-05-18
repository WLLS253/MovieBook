package com.movie.Exception;

import com.movie.Enums.ExceptionEnums;

public class AuthorException extends  RuntimeException {

    private Integer code;

    public AuthorException(ExceptionEnums exceptionEnums){
        super(exceptionEnums.getMsg());
        this.code=exceptionEnums.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
