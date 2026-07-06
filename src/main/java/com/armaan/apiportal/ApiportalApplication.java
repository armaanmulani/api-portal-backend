package com.armaan.apiportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiportalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiportalApplication.class, args);
	}

}
