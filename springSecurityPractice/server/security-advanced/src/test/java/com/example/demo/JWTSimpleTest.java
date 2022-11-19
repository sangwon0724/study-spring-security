package com.example.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JWTSimpleTest {
	/*
	 	[JWT 스펙에서 지정한 claim]
	 	
		● iss : Issuer 토큰을 발행한 사람(단체,사이트)이 누구인지
		● sub : Subject 무엇에 관한 토큰인지
		● aud : Audience 누구를 대상으로 한 토큰인지
		● exp : Expiration 토큰의 만료 시간은 언제인지
		● nbf : Not Before 토큰이 언제부터 유효한지
		● iat : Issued At 토큰 발행 시간
		● jti : JWT ID : 토큰 자체의 아이디(일련번호같은 개념)
		● 그 밖에 인증에 필요하거나 대상서버에서 필요로 하는 데이터
		
		[토큰에 들어가는 내용]

		● 인증에 필요한 최소한의 데이터 (일반적인 경우)
		● 비밀번호나 전화번호 등의 개인정보를 넣는 것은 안전하지 않다.
		● JWT에는 주로 언제든 공개할 수 있는 정보를 넣는 것이 좋다.
		● 서버에서 인증된 키가 아니라도 언제든 서버는 해당 토큰을 열어서 그 안에 어떤 Claim 이 있는지를 볼 수 있다.
		
		[토큰의 관리 방법]
		
		● 기본 이론에서는 토큰을 클라이언트가 관리하도록 한다.
		● 그러나 실제로는 여러 이유로 서버에서 토큰을 관리하도록 한다.
			○ 사용자 정보 캐싱
			○ 토큰의 유효성 평가
			○ refresh 토큰 정책
		● 이 경우, 토큰과 사용자 정보를 관리하는 방법으로 다음과 같은 방법들을 사용하기도 합니다.
			○ Redis나 Hazelcast같은 메모리 데이터 이용 방식
			○ Database 저장
	*/

	//토큰의 header와 body의 데이터 출력
    private void printToken(String token){
        String[] tokens = token.split("\\."); //"\\."로 header와 body를 분리
        System.out.println("header: "+new String(Base64.getDecoder().decode(tokens[0]))); //base64로 디코드
        System.out.println("body: "+new String(Base64.getDecoder().decode(tokens[1]))); //base64로 디코드
    }

    @DisplayName("1. jjwt를 이용한 토큰 테스트")
    @Test
    void test_1(){
        //jjwt를 통해 생성한 JWT (builder 사용)
        String okta_token
        	= Jwts.builder()
        		.addClaims( Map.of("name", "sangwon", "price", 3000) ) /* 값 추가 */
        		.signWith(SignatureAlgorithm.HS256, "sangwon") /* signWith(알고리즘, 키 값) */
                .compact(); /* JWT를 빌드하고 JWT 압축 직렬화 규칙에 따라 압축된 안전한 URL 문자열로 직렬화 */
        
        System.out.println(okta_token); //JWT 토큰 출력
        printToken(okta_token); //base64로 디코딩한 토큰에서 header와 body를 각각 출력

        Jws<Claims> tokenInfo
        	= Jwts.parser() /* JWT를 해석하는데 사용할수 있는 인스턴스 반환 */
        		.setSigningKey("sangwon") /* 해당 토큰 생성시 사용한 키 값을 설정 */
        		.parseClaimsJws(okta_token); /* 해석할 JWT 설정 */
        System.out.println(tokenInfo); /* 해석한 데이터 출력 */
    }


    @DisplayName("2. java-jwt를 이용한 토큰 테스트")
    @Test
    void test_2() {
        byte[] SEC_KEY = DatatypeConverter.parseBase64Binary("sangwon"); // 키 값

        //java-jwt를 통해 생성한 JWT (create 사용)
        String oauth0_token
        	= JWT.create()
        		.withClaim("name", "sangwon") /* 값 설정 */
        		.withClaim("price", 3000) /* 값 설정 */
                .sign(Algorithm.HMAC256(SEC_KEY)); /* sign(Algorithm.사용할_알고리즘명(base64 방식으로 변환한 키 값)) */

        System.out.println(oauth0_token); //JWT 토큰 출력
        printToken(oauth0_token); //base64로 디코딩한 토큰에서 header와 body를 각각 출력

        DecodedJWT verified
        	= JWT.require(Algorithm.HMAC256(SEC_KEY)) /* 키 값 설정 */
	        	.build() /* 인스턴스 생성 */
	        	.verify(oauth0_token); /* 해석할 토큰 설정 */
        System.out.println(verified.getClaims()); //body의 데이터만 출력

        Jws<Claims> tokenInfo
        	= Jwts.parser() /* JWT를 해석하는데 사용할수 있는 인스턴스 반환 */
        		.setSigningKey(SEC_KEY) /* 해당 토큰 생성시 사용한 키 값을 설정 */
        		.parseClaimsJws(oauth0_token); /* 해석할 JWT 설정 */
        System.out.println(tokenInfo); //header와 body의 데이터 출력
    }

    @DisplayName("3. 만료 시간 테스트")
    @Test
    void test_3() throws InterruptedException {
        final Algorithm AL = Algorithm.HMAC256("sangwon"); //알고리즘 및 키 값 설정

        //java-jwt를 통해 생성한 JWT (create 사용)
        String token
        	= JWT.create()
        		.withSubject("a1234") /* 무엇에 관한 토큰인지 */
                .withNotBefore(new Date(System.currentTimeMillis() + 1000)) /* 토큰이 언제부터 유효한지 */
                .withExpiresAt(new Date(System.currentTimeMillis() + 3000)) /* 토큰의 만료 시간은 언제인지 */
                .sign(AL); /* sign(Algorithm.사용할_알고리즘명(base64 방식으로 변환한 키 값)) */

        try {
            DecodedJWT verify
            	= JWT.require(AL) /* 키 값 설정 */
	            	.build() /* 인스턴스 생성 */
	            	.verify(token); /* 해석할 토큰 설정 */
            System.out.println(verify.getClaims()); /* body 부분 출력 */
        } catch(Exception e){
            System.out.println("유효하지 않은 토큰입니다...");
            DecodedJWT decode = JWT.decode(token);
            System.out.println(decode.getClaims()); /* body 부분 출력 */
        }

    }
}
