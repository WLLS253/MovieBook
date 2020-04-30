package com.movie.Action;
import com.opensymphony.xwork2.ActionSupport;

import org.springframework.beans.factory.annotation.Autowired;

public class HelloAction extends ActionSupport {

//    @Autowired
//    private UserService userService;

    @Override
    public String execute() throws Exception {
        System.out.println("我被执行了吗");
        return SUCCESS;
    }

}