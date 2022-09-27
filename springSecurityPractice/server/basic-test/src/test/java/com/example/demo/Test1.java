package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Test1 {
	@DisplayName("1. 테스트")
	@Test
	void test_1() {
		assertEquals("test", "test");
	}
}
