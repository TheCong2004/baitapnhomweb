package com.example.webnhom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // ✅ Quan trọng: Phải có annotation này
public class WebnhomApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebnhomApplication.class, args);
    }
}