package com.pallabi.scheduler.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({"com.pallabi.scheduler.utils.service","com.pallabi.scheduler.utils.config"})
@EnableJpaRepositories("com.pallabi.scheduler.utils.repository")
@EntityScan("com.pallabi.scheduler.utils.model.dto")
public class SchedulerConfig {
}
