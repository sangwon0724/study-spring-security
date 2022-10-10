package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*

	1. @SpringBootApplication을 읽어올수 없을 때는
	   프로젝트 우클릭 > Properties > Project Natures에서 Gradle Project Nature가 추가되어있나 확인하기
	2. 깃허브에서 받아왔을 때 [File 메뉴 > Import > Gradle > Existing Gradle Project를 실행 후 연결할 프로젝트 선택]을 진행하면
	   정상적으로 그래들이 연동된 상태의 프로젝트를 연결할수 있다.
	3. 멀티 모듈의 경우 [Git Repositories > Working Tree에서 해당 모듈 선택 > 우클릭 > Import Projects...]를 진행하면
	   디렉터리 구조가 아닌 프로젝트 구조로 check out 받게 된다. (Git의 경우)
*/

/*
 	1. basic-test
 	2. login-basic
 	3. login-custom-filter
 	4. basic-authentication-test
 	5. login-multi-chain
 	6. login-userdetails
 	7. login-rememberme (로그인 기억하기)
 	8. login-session-management (세션 관리)
 	9. authority-test (권한 관리)
 		1) 권한 테스트
 		2) voter
 		3) 메소드의 후처리
 		4) Secured 기반 권한 체크
 		5) 임시권한 부여 <= 진행중
*/

@SpringBootApplication
public class SpringSecurityPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityPracticeApplication.class, args);
	}

}
