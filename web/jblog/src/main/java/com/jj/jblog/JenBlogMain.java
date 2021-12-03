package com.jj.jblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 张俊杰
 * @date 2021/11/5  - {TIME}
 */
@SpringBootApplication
@MapperScan("com.jj.jblog.dao")
public class JenBlogMain {
    public static void main(String[] args) {
        SpringApplication.run(JenBlogMain.class,args);
    }
}
