package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*

1. @SpringBootApplication을 읽어올수 없을 때는
   프로젝트 우클릭 > Properties > Project Natures에서 Gradle Project Nature가 추가되어있나 확인하기
*/

@SpringBootApplication
public class ServerBasicTestApplication {
	//psvm을 자동 완성하면 main 함수가 된다.
	public static void main(String[] args) {
		Person person = new Person(); //롬복이 일을 안함
		//Person person2 = Person.builder().name("test").build();
		//System.out.println(person);
		SpringApplication.run(ServerBasicTestApplication.class, args);
	}

}
