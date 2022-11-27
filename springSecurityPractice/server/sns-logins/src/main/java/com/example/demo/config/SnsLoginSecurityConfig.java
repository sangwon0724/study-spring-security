package com.example.demo.config;

import com.example.demo.user.domain.SpOauth2User;
import com.example.demo.user.domain.SpUser;
import com.example.demo.user.service.SpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SnsLoginSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private SpUserService userService;

    @Autowired
    private SpOAuth2UserService oAuth2UserService;

    @Autowired
    private SpOidcUserService oidcUserService;
    //private OidcUserService oidcUserService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    DaoAuthenticationProvider daoAuthenticationProvider;

    
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
        //http.oauth2Login();
    	
    	http
    		//.formLogin().and()
      .oauth2Login(
              oauth2->
                      oauth2.userInfoEndpoint(
                		  userInfo-> userInfo.userService(oAuth2UserService).oidcUserService(oidcUserService)
                      )
                      .successHandler(new AuthenticationSuccessHandler() {
                          @Override
                          public void onAuthenticationSuccess(
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  Authentication authentication
                          ) throws IOException, ServletException {

                              Object principal = authentication.getPrincipal();

                              if(principal instanceof OAuth2User){
                                  if(principal instanceof OidcUser){
                                      // google
                                      SpOauth2User googleUser = SpOauth2User.OAuth2Provider.google.convert((OAuth2User) principal);
                                      SpUser user = userService.loadUser(googleUser);
                                      SecurityContextHolder.getContext().setAuthentication(
                                              new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities())
                                      );
                                  }else{
                                      // naver, or kakao, facebook
                                      SpOauth2User naverUser = SpOauth2User.OAuth2Provider.naver.convert((OAuth2User) principal);
                                      SpUser user = userService.loadUser(naverUser);
                                      SecurityContextHolder.getContext().setAuthentication(
                                              new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities())
                                      );
                                  }
                                  System.out.println(principal);
                                  request.getRequestDispatcher("/").forward(request, response);
                              }

                          }
                      })

		//.and()
		//.addFilterAfter(userTranslateFilter, OAuth2LoginAuthenticationFilter.class)
      );
    }
}
