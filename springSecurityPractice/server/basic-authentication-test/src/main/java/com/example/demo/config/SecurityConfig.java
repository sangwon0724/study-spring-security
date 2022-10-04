package com.example.demo.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	/*
		BasicAuthenticationFilter : 기본적으로 로그인 페이지를 사용할 수 없는 상황에서 사용 (예시 : SPA 페이지, 브라우저 기반 모바일 앱)
		
		기본 소스)
		  //전제 조건 : 해당 클래스가 WebSecurityConfigurerAdapter 상속
		  @Override
		  protected void configure(HttpSecurity http) throws Exception {
		      http
		              .httpBasic()
		              ;
		  }
		  
		● 최초 로그인시에만 인증을 처리하고, 이후에는 session에 의존
		● RememberMe를 설정한 경우, remember-me 쿠키가 브라우저에 저장되기 때문에
		  세션이 만료된 이후라도 브라우저 기반의 앱에서는 장시간 서비스를 로그인 페이지를 거치지 않고 이용할 수 있다.
		● 에러가 나면 401 (UnAuthorized) 에러를 발생시킨다.
	*/
    BasicAuthenticationFilter filter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(
                        User.withDefaultPasswordEncoder()
                        .username("user1")
                        .password("1111")
                        .roles("USER")
                        .build()
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic()
                ;
    }

}
