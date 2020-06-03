package com.movie.Plugins;

import org.springframework.context.annotation.Configuration;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;


@Configuration
@WebListener
public class UserStatisticsListener implements HttpSessionListener {
    public static AtomicInteger userCount = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("创建session");
        userCount.getAndIncrement();
        System.out.println(userCount);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("销毁session");
        userCount.getAndDecrement();

    }
}
