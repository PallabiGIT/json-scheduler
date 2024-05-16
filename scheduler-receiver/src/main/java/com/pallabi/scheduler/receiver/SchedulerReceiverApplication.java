package com.pallabi.scheduler.receiver;

import com.pallabi.scheduler.receiver.config.ReceiverConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ReceiverConfig.class)
public class SchedulerReceiverApplication {
	public static void main(String[] args) {
		SpringApplication.run(SchedulerReceiverApplication.class, args);
	}
}