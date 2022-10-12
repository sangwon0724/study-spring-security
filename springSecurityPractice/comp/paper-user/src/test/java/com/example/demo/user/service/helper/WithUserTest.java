package com.example.demo.user.service.helper;

import com.example.demo.user.domain.School;
import com.example.demo.user.repository.SchoolRepository;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.SchoolService;
import com.example.demo.user.service.UserSecurityService;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

public class WithUserTest {

    @Autowired
    protected SchoolRepository schoolRepository;
    @Autowired
    protected UserRepository userRepository;

    protected SchoolService schoolService;
    protected UserService userService;
    protected UserSecurityService userSecurityService;

    protected SchoolTestHelper schoolTestHelper;
    protected UserTestHelper userTestHelper;
    protected School school;

    private boolean prepared;

    protected void prepareUserServices (){
        if(prepared) return;
        prepared = true;

        this.schoolRepository.deleteAll();
        this.userRepository.deleteAll();
        this.schoolService = new SchoolService(schoolRepository);
        this.userService = new UserService(schoolRepository, userRepository);
        this.userSecurityService = new UserSecurityService(userRepository);

        this.userTestHelper = new UserTestHelper(userService, NoOpPasswordEncoder.getInstance());
        this.schoolTestHelper = new SchoolTestHelper(schoolService);
        this.school = this.schoolTestHelper.createSchool("테스트 학교", "서울");
    }

}
