package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.domain.SpUser;

@RestController
public class HomeController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public Object greeting(@AuthenticationPrincipal Object user){
        return user;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public SpUser greeting(@AuthenticationPrincipal SpUser user){
        return user;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/greeting")
    public OAuth2User greeting(@AuthenticationPrincipal OAuth2User user){
        return user;
    }
}
