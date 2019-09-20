package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//        (scanBasePackages={"com.example.controller","com.example.config","com.example.service"})
@EnableTransactionManagement // 配置的 事务注解的开发
@MapperScan(basePackages = "com.example.mapper") // tkmapper的扫描
public class WfxZimeitiApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(WfxZimeitiApplication.class, args);
    }

}
