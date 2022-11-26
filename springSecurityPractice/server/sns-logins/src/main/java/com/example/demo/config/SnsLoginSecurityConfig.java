package com.example.demo.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SnsLoginSecurityConfig extends WebSecurityConfigurerAdapter {

    private OidcUserService oidcUserService;
    
    /*
     	[구글 기준]
     	1. https://console.cloud.google.com/로 이동
     	2. 프로젝트 생성
     	3. 프로젝트 선택
     	4. 대시보드로 이동하거나 혹은 좌측의 슬라이드 메뉴 오픈
     	5. OAuth 동의 화면 구성
     	6. "API 및 서비스" > "사용자 인증 정보" > "사용자 인증 정보 만들기" > "OAuth 클라이언트 ID" 클릭
     	7. 관련 사항 입력 후 저장 (리다이렉트 주소 : [도메인] + "/login/oauth2/code/google"
     	
     	+ [도메인] + "/login?logout"로 보내면 로그아웃 된다.
    */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2Login();
    }
}
