package com.inexture.UserFinalTaskBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.inexture")
@SpringBootApplication
public class UserFinalTaskBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserFinalTaskBootApplication.class, args);
	}

}
