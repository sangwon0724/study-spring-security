package com.example.demo.paper;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.

import java.util.Optional;

public interface PaperRepository extends JpaRepository<Paper, Long> {

	//@Cacheable(value="papers")
    Optional<Paper> findById(Long id);

}
