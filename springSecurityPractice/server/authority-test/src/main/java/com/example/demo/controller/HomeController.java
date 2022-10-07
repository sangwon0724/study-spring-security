package com.example.demo.controller;

import com.example.demo.service.SecurityMessageService;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    MethodSecurityInterceptor methodSecurityInterceptor;

    private final SecurityMessageService securityMessageService;

    //생성자 주입
    public HomeController(SecurityMessageService securityMessageService) {
        this.securityMessageService = securityMessageService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/greeting/{name}")
    public String greeting(@PathVariable String name){
        return "hello " + securityMessageService.message(name);
    }

}
