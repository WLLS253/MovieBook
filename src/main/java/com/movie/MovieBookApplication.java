package com.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableScheduling
@SpringBootApplication
public class MovieBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieBookApplication.class, args);
    }

}
