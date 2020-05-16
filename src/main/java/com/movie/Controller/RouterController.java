package com.movie.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin
@Controller
public class RouterController {

    @RequestMapping({"/","/index"})
    public String index(){
        return "index";
    }

    @RequestMapping({"/mng/","/mng/index"})
    public String mngIndex(){
        return "xdq/index_xdq_new";
    }

    @RequestMapping({"/sys/","/sys/index"})
    public String sysIndex(){
        return "lqw/index";
    }

    @RequestMapping(value = "/mng/{htmlname}.html")
    public  String getMngPage(@PathVariable(name = "htmlname") String html_name) {
        return "xdq/"+html_name;
    }


    @RequestMapping(value = "/user/{htmlname}.html")
    public  String getUserPage(@PathVariable(name = "htmlname") String html_name) {
        return "zhw/"+html_name;
    }


    @RequestMapping(value = "/Sys/{htmlname}.html")
    public  String getSysPage(@PathVariable(name = "htmlname") String html_name) {
        return "lqw/"+html_name;
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "views/login";
    }


}
