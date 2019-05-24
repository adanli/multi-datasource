package com.multi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author adanl
 */
@SpringBootApplication
@MapperScan("com.multi.common.db.mapper.*")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class);
        System.out.println("ok");
    }
}
