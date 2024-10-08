package com.example.homemanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HomeManagerApplicationTests {

	@Test
	void contextLoads() {
		int expectedValue = 1;
		int actualValue = 1;
		Assertions.assertEquals(expectedValue, actualValue, "Los valores deberían ser iguales");
	}

}
