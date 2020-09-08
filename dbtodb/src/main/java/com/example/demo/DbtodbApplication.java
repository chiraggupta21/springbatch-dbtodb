package com.example.demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class DbtodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbtodbApplication.class, args);
	}

}
