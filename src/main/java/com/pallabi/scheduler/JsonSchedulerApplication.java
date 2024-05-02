package com.pallabi.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JsonSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonSchedulerApplication.class, args);
	}

}
