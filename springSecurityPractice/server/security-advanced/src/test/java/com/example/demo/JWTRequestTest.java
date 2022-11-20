package com.example.demo;

import com.example.demo.user.domain.SpUser;
import com.example.demo.user.repository.SpUserRepository;
import com.example.demo.user.service.SpUserService;
import com.example.demo.config.UserLoginForm;
import com.example.demo.test.WebIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JWTRequestTest extends WebIntegrationTest {


    @Autowired
    private SpUserRepository userRepository;

    @Autowired
    private SpUserService userService;

    @BeforeEach
    void before(){
        userRepository.deleteAll(); //회원 삭제

        SpUser user
        	= userService.save(
        		SpUser.builder()
                .email("user1")
                .password("1111")
                .enabled(true)
                .build()
               ); //회원 정보 저장
        userService.addAuthority(user.getUserId(), "ROLE_USER"); //권한 추가
    }

    private TokenBox getToken(){
        RestTemplate client = new RestTemplate();
        
        HttpEntity<UserLoginForm> body = new HttpEntity<>(
                UserLoginForm.builder().username("user1").password("1111").build()
        );
        
        ResponseEntity<SpUser> resp1 = client.exchange(uri("/login"), HttpMethod.POST, body, SpUser.class);
        
        return TokenBox.builder().authToken(resp1.getHeaders().get("auth_token").get(0))
                .refreshToken(resp1.getHeaders().get("refresh_token").get(0))
                .build();
    }

    private TokenBox refreshToken(String refreshToken){
        RestTemplate client = new RestTemplate();
        HttpEntity<UserLoginForm> body = new HttpEntity<>(
                UserLoginForm.builder().refreshToken(refreshToken).build()
        );
        ResponseEntity<SpUser> resp1 = client.exchange(uri("/login"), HttpMethod.POST, body, SpUser.class);
        
        return TokenBox.builder().authToken(resp1.getHeaders().get("auth_token").get(0))
                .refreshToken(resp1.getHeaders().get("refresh_token").get(0))
                .build();
    }

    @DisplayName("1. hello 메시지를 받아온다... ")
    @Test
    void test_1(){
        /*RestTemplate client = new RestTemplate();

        HttpEntity<UserLoginForm> body = new HttpEntity<>(
                UserLoginForm.builder().username("user1").password("1111").build()
        );

        ResponseEntity<SpUser> resp1 = client.exchange(uri("/login"), HttpMethod.POST, body, SpUser.class);
        System.out.println(resp1.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
        System.out.println(resp1.getBody());

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.AUTHORIZATION, resp1.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
        body = new HttpEntity<>(null, header);
        ResponseEntity<String> resp2 = client.exchange(uri("/greeting"), HttpMethod.GET, body, String.class);

        assertEquals("hello", resp2.getBody()); //값이 동일한지 확인 */
        
        TokenBox token = getToken();

        RestTemplate client = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAuthToken());
        HttpEntity body = new HttpEntity<>(null, header);
        ResponseEntity<String> resp2 = client.exchange(uri("/greeting"), HttpMethod.GET, body, String.class);

        assertEquals("hello", resp2.getBody()); //값이 동일한지 확인

    }


    @DisplayName("2. 토큰 만료 테스트 ")
    @Test
    void test_2() throws InterruptedException {
        TokenBox token = getToken();

        Thread.sleep(3000);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.AUTHORIZATION, "Bearer "+token.getAuthToken());
        RestTemplate client = new RestTemplate();
        assertThrows(Exception.class, ()->{
            HttpEntity body = new HttpEntity<>(null, header);
            ResponseEntity<String> resp2 = client.exchange(uri("/greeting"), HttpMethod.GET, body, String.class);
        });

        token = refreshToken(token.getRefreshToken());
        HttpHeaders header2 = new HttpHeaders();
        header2.add(HttpHeaders.AUTHORIZATION, "Bearer "+token.getAuthToken());
        HttpEntity body = new HttpEntity<>(null, header2);
        ResponseEntity<String> resp3 = client.exchange(uri("/greeting"), HttpMethod.GET, body, String.class);

        assertEquals("hello", resp3.getBody());
    }

}
