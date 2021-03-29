package com.enrollment.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = EnrollmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EnrollmentApplicationTests {

	@Test
	void testMain() {
		EnrollmentApplication.main(new String[0]);
		EnrollmentApplication.exit();
		assertTrue(true);
	}

}
