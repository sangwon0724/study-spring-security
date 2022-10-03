package com.example.demo.cofing;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true) //권한 체크 모듈 작동
//@Order(1) //필더의 적용 순서를 나타내는 어노테이션, 값이 낮을수록 우선 적용
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	/*
		configure 함수 : 필터를 세팅하는 역할
	*/
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//super.configure(auth);
		
		//메모리용 접속 권한 추가
		//★★★ 유저에 대한 Authentication Provider를 추가하게 되면 application.properties의 설정들은 사용할수 없다.
		auth.inMemoryAuthentication()
		.withUser(User.builder().username("user2").password(passwordEncoder().encode("2222")).roles("USER"))
		.withUser(User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN"));
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super.configure(http);
		
		/*
			antMatchers : 필터를 적용할 url 패턴 명시 (예시 : /api/** => /api 이하의 url에 대해서 필터 적용)
		*/
		
		//http.formLogin(); //form login 필터 설정
		//http.httpBasic(); //http basic 필터 설정
		
		//http.cors().disable(); //cors 필터 해제

		//http.formLogin().and().authorizeRequests(auth->{auth.anyRequest().permitAll();});
		
		//http.authorizeHttpRequests((requests)->requests.antMatchers("/").permitAll().anyRequest().authenticated());
		
		http.headers().disable()
		.csrf().disable()
		.formLogin(login->login.defaultSuccessUrl("/",false))
		.logout().disable()
		.requestCache().disable();
		
		
	}
}
