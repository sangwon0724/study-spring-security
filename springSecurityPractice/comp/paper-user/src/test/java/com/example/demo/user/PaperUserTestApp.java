package com.example.demo.user;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class PaperUserTestApp {

    public static void main(String[] args) {
        SpringApplication.run(PaperUserTestApp.class, args);
    }

    @Configuration
    @ComponentScan("com.example.demo.user")
    @EnableJpaRepositories(basePackages = {
            "com.example.demo.user.repository"
    })
    @EntityScan(basePackages = {
            "com.example.demo.user.domain"
    })
    class Config {

    }

}
