package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "om.example.demo.config",
        "om.example.demo.web",
        "om.example.demo.site"
})
public class OnlinePaperApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinePaperApplication.class, args);
    }

}
