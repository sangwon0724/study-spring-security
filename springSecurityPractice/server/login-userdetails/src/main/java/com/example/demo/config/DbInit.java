package com.example.demo.config;

import com.example.demo.user.domain.SpAuthority;
import com.example.demo.user.domain.SpUser;
import com.example.demo.user.repository.SpUserRepository;
import com.example.demo.user.service.SpUserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DbInit implements InitializingBean {

    @Autowired
    private SpUserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
    	/* email이 "user@test.com"인 회원이 없으면
    	 * 이메일은 "user@test.com"이고, 비밀번호는 "1111"인 회원을 추가 및 저장한다.
    	*/
        if(!userService.findUser("user@test.com").isPresent()){
            SpUser user = userService.save(
			            		SpUser.builder()
			                    .email("user@test.com")
			                    .password("1111")
			                    .enabled(true)
			                    .build()
		            		);
            //변수 user의 id를 가져와서 해당 아이디를 가진 회원의 권한을 USER로 준다.
            //user가 첫번째 데이터면 자동증가 설정에 인해서 1이 된다.
            userService.addAuthority(user.getUserId(), "ROLE_USER");
        }


    }



}
