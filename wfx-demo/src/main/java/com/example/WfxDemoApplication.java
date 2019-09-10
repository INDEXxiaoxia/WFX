package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication //springboot的启动文件（注意：一个工程中，只允许有一个启动文件）
@EnableTransactionManagement // 配置的 事务注解的开发
@MapperScan(basePackages = "com.example.mapper") // tkmapper的扫描
public class WfxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WfxDemoApplication.class, args);
    }

}
