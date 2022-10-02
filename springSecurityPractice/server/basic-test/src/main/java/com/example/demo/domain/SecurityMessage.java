package com.example.demo.domain;

import org.springframework.security.core.Authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Builder가 실행이 안됨
/*
	1. Lombok의 어노테이션 class 파일로 이동
	2. Project Exploer 우측 상단의 상호화살표 모양읠 Link With Editor 클릭
	3. Lombok.jar의 설치 위치 확인
	4. 파일 탐색기에서 해당 위치로 이동하여 해당 jar 파일 실행
	5. 사용하는 개발환경(이클립스)에 설치
	6. 이클립스에서 Project > Clean... 실행
	7. 이클립스 재시작
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecurityMessage {
	private Authentication auth;
	private String message;
}
