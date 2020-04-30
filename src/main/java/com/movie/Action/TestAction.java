package com.movie.Action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;


public class TestAction extends ActionSupport {

    @Override
    public String execute() throws Exception {
       return  SUCCESS;
    }
}
