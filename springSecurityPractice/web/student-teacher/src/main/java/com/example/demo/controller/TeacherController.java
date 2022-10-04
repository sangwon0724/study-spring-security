package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.student.StudentManager;
import com.example.demo.teacher.Teacher;


@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private StudentManager studentManager;
    
    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER')")
    @GetMapping("/main")
    public String main(@AuthenticationPrincipal Teacher teacher, Model model){
        model.addAttribute("studentList", studentManager.myStudents(teacher.getId()));
        return "TeacherMain";
    }


}
