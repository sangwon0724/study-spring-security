package com.example.demo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("com.example.demo.user")
@EntityScan(basePackages = {
        "com.example.demo.user.domain"
})
@EnableJpaRepositories(basePackages = {
        "com.example.demo.user.repository"
})
public class UserAdminModule {
}
