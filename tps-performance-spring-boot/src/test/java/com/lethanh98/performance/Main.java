package com.lethanh98.performance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication()
@EnableCaching// không dùng thì tắt bỏ
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
