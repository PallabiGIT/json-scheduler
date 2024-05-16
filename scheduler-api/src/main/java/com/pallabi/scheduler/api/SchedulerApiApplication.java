package com.pallabi.scheduler.api;

import com.pallabi.scheduler.api.config.APIConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(APIConfig.class)
public class SchedulerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerApiApplication.class, args);
	}

}
