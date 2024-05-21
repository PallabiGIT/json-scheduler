package com.pallabi.scheduler.api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({"com.pallabi.scheduler.utils.service"})
@EnableJpaRepositories("com.pallabi.scheduler.utils.repository")
@EntityScan("com.pallabi.scheduler.utils.model.dto")
@Import(SecurityConfiguration.class)
public class APIConfig {
}
