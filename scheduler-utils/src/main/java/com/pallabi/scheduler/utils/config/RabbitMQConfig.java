package com.pallabi.scheduler.utils.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbit.mq.queue.customer}")
    private String queueCustomer;

    @Value("${rabbit.mq.queue.complex.json}")
    private String queueComplexJSON;

    @Bean
    public Queue createCustomerQueue() {
        return new Queue(queueCustomer);
    }

    @Bean
    public Queue createComplexJSONQueue() {
        return new Queue(queueComplexJSON);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

}