package com.movie.Controller;


import com.movie.Result.Result;
import com.movie.Util.Util;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StrutsTestController extends ActionSupport {

    private String username;
    private String password;

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

    @RequestMapping(value = "/index.html")
    public  String index(){
        System.out.println("asfvsfdddfsad");
        return "/xdq/xdq_cinema_arrangements" ;
    }

    @RequestMapping(value = "/{htmlname}.html")
    public String getPage(@PathVariable(name = "htmlname") String html_name) {
        return html_name;
    }

    @RequestMapping(value = "/test2.html")
    public String test(){
        System.out.println("+++++++++++++++++++++++++++++");
        return "test2";
    }





    @Action(value = "test")
    public String testaction(){
        System.out.println("我终于被使用了");
        if (username.equals("admin") && password.equals("123")) {
            System.out.println(SUCCESS);
            return SUCCESS;
        } else {
            return LOGIN;
        }
    }
}
