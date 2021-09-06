package com.snowwarrior.huikuan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.snowwarrior.huikuan.mapper")
public class HuikuanApplication {
    public static void main(String[] args) {
        SpringApplication.run(HuikuanApplication.class, args);
    }
}
