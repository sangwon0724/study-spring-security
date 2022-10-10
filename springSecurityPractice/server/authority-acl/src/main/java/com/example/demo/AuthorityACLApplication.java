package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.example.demo.config",
        "com.example.demo.paper"
})
public class AuthorityACLApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityACLApplication.class, args);
    }

}
