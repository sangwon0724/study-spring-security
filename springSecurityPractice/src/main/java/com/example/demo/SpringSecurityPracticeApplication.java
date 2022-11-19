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
	4. Gradle Refresh가 정상적으로 진행이 안될 경우 : C:\Users\[유저명]\.gradle\caches 폴더에 있는 데이터 싹 다 제거 후 재시도
	5. Gradle 버전 확인 : gradle 폴더 > wrapper 폴더 > gradle-wrapper.properties 파일 > distributionUrl 속성
*/

/*
	[server]
	 	01. basic-test
	 	02. login-basic
	 	03. login-custom-filter
	 	04. basic-authentication-test
	 	05. login-multi-chain
	 	06. login-userdetails
	 	07. login-rememberme (로그인 기억하기)
	 	08. login-session-management (세션 관리)
	 	09. authority-test (권한 관리)
	 		1) 권한 테스트
	 		2) voter
	 		3) 메소드의 후처리
	 		4) Secured 기반 권한 체크
	 		5) 임시권한 부여
	 	10. authority-acl (도메인 객체 보안 : ACL)
	 	11. server-security-advanced (JWT + Auth Token)
*/

/*
	< 프로젝트 실습 >
	
	[comp]
	 	01. paper-user (도메인 : 학교, 선생님, 학생)
	 	02. paper (시험지)
	 	
	[server]
		01. paper-app
	 	
	[web]
		01. site-manager
		02. site-student
		03. site-teacher
*/

@SpringBootApplication
public class SpringSecurityPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityPracticeApplication.class, args);
	}

}
