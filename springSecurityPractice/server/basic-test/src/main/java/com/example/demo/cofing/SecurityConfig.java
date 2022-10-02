package com.example.demo.cofing;

import org.springframework.context.annotation.Bean;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter{
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
		http.authorizeHttpRequests((requests)->requests.antMatchers("/").permitAll().anyRequest().authenticated());
		//http.formLogin();
		//http.httpBasic();
		
		//http.formLogin().and().authorizeRequests(auth->{auth.anyRequest().permitAll();});
	}
}
