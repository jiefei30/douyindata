package com.marchsoft.douyin;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author Wangmingcan
 * @date 2021/8/23 10:02
 * @description
 */
@RestController
@SpringBootApplication
@MapperScan("com.marchsoft.douyin.mapper")
public class RunApp {
    public static void main(String[] args) {
        SpringApplication.run(RunApp.class, args);
    }

    @GetMapping("/")
    public String status () {
        return "Backend service started successfully";
    }
}
