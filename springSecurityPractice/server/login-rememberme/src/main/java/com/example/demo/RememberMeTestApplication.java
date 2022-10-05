package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.example.demo.config",
        "com.example.demo"
})
public class RememberMeTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RememberMeTestApplication.class, args);
    }

}
