package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableTransactionManagement // 配置的 事务注解的开发
@MapperScan(basePackages = "com.example.mapper") // tkmapper的扫描
public class WfxElasticsearchDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WfxElasticsearchDemoApplication.class, args);
    }

}
