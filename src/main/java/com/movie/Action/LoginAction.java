package com.movie.Action;


import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;



//@Action(value = "Login",results = {@Result(name = "success",location = "/success.jsp")})
public class LoginAction extends ActionSupport {
    private String username;
    private String password;

    @Override
    public String execute() throws Exception {
        if (username.equals("admin") && password.equals("123")) {
            System.out.println(SUCCESS);
            return SUCCESS;
        } else {
            return LOGIN;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
