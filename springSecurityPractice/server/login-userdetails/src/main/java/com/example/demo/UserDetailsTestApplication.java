 package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.example.demo.config",
        "com.example.demo.web"
})

//http://localhost:8091/h2-console로 이동시 h2 콘솔 사용 가능 (application.properties에서 설정)
public class UserDetailsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDetailsTestApplication.class, args);
    }

}
