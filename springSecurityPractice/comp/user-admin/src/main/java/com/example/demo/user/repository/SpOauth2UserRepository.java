package com.example.demo.user.repository;

import com.example.demo.user.domain.SpOauth2User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SpOauth2UserRepository extends JpaRepository<SpOauth2User, String> {

}
