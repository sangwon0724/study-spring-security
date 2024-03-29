package com.example.demo.cofing;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final CustomAuthDetail customAuthDetail;

    public SecurityConfig(CustomAuthDetail customAuthDetail) {
        this.customAuthDetail = customAuthDetail;
    }

    //권한의 상하관계를 표시
    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER"); //관리자가 사용자보다 높음을 표시, 설정시 관리자가 사용자 페이지에 접근 가능
        return roleHierarchy;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(
                        User.withDefaultPasswordEncoder()
                                .username("user1")
                                .password("1111")
                                .roles("USER")
                ).withUser(
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("2222")
                        .roles("ADMIN")
        );
    }
    
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(request->{
             request.antMatchers("/**").permitAll().anyRequest().authenticated();
        }).formLogin(
        		login->login.loginPage("/login")
        		.permitAll()
        		.defaultSuccessUrl("/", false)
        		.failureUrl("/login-error")
        ).logout(logout->logout.logoutSuccessUrl("/"))
        .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"));
    }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		//super.configure(web);
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
}
