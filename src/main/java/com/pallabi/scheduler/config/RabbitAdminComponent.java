package com.pallabi.scheduler.config;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitAdminComponent implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        rabbitAdmin.initialize();
    }
}