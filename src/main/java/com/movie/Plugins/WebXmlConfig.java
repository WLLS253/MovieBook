//package com.example.demo.Plugins;
//
//
//import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class WebXmlConfig {
//
//
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean frgb = new FilterRegistrationBean();
//
//        frgb.setFilter(new StrutsPrepareAndExecuteFilter());
//
//        List list = new ArrayList();
//        list.add("/*");
//        list.add("*.action");
//        frgb.setUrlPatterns(list);
//
//        System.out.println("我被使用le");
//        System.out.println(frgb.getUrlPatterns());
//        System.out.println(frgb.getFilter());
//        return frgb;
//    }
////    @Bean
////    public StrutsPrepareAndExecuteFilter strutsPrepareAndExecuteFilter(){
////        System.out.println(new StrutsPrepareAndExecuteFilter());
////        return new StrutsPrepareAndExecuteFilter();
////
////    }
//
//
//
//}
