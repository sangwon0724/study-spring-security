package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;

@SpringBootApplication
public class SnsLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnsLoginApplication.class, args);
    }

}
