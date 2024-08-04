package com.kth.project_dollarstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Entry point for the Spring Boot application with asynchronous processing.
 */
@SpringBootApplication
@EnableAsync
public class ProjectDollarstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectDollarstoreApplication.class, args);
	}
}