package com.pallabi.scheduler.utils;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class TestUtils {
    public static int getMessageCount(RabbitAdmin rabbitAdmin, String queueName){
        return rabbitAdmin.getQueueInfo(queueName).getMessageCount();

    }

    public static String getMessageContent(RabbitTemplate rabbitTemplate, String queueName){
        return rabbitTemplate.receiveAndConvert(queueName).toString();
    }
}
