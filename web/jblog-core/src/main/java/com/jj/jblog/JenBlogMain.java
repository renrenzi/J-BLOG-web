package com.jj.jblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 任人子
 * @date 2021/11/5  - {TIME}
 */
@SpringBootApplication(scanBasePackages = {"com.jj.jblog"})
public class JenBlogMain {
    public static void main(String[] args) {
        SpringApplication.run(JenBlogMain.class,args);
    }
}
