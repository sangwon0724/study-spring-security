package com.example.demo.paper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.example.demo.config"
})
public class PaperTestApp {

    public static void main(String[] args) {
        SpringApplication.run(PaperTestApp.class, args);
    }

    @Configuration
    @EntityScan(basePackages = {
            "com.example.demo.user.domain",
            "com.example.demo.paper.domain"
    })
    @EnableJpaRepositories(basePackages = {
            "com.example.demo.user.repository",
            "com.example.demo.paper.repository"
    })
    class JpaConfig {

    }
}
