package com.example.demo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {
		com.example.demo.paper.Paper.class
})
@EnableJpaRepositories(basePackageClasses = {
		com.example.demo.paper.PaperRepository.class
})
public class JpaConfig {
}
