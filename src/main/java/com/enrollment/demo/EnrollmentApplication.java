package com.enrollment.demo;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EnrollmentApplication {

	static ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(EnrollmentApplication.class, args);
	}

	public static void exit() {
		SpringApplication.exit(applicationContext, new ExitCodeGenerator[0]);
	}

}
