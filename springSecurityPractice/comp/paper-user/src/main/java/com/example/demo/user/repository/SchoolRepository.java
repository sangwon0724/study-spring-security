package com.example.demo.user.repository;

import com.example.demo.user.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query("select distinct(city) from School")
    List<String> getCities();

    List<School> findAllByCity(String city);

}
