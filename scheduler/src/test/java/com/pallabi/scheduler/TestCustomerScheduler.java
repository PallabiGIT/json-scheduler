package com.pallabi.scheduler;


import com.pallabi.scheduler.rabbitmq.config.TestConfig;
import com.pallabi.scheduler.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Import(TestConfig.class)
public class TestCustomerScheduler {
    @Autowired
    private CustomerScheduler customerScheduler;

    @Value("classpath:complex_input.json")
    private Resource sampleComplexJSON;

    @Value("${customer.cron.file.location}")
    private String cronFileDestinationLocation;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbit.mq.queue.complex.json}")
    private String queueComplexJson;

    @BeforeEach
    public void setup() throws IOException {
        File src = sampleComplexJSON.getFile();
        File dest = new File(cronFileDestinationLocation);
        if(!dest.exists()) {
            FileSystemUtils.copyRecursively(src, dest);
        }
    }

    @Test
    public void shouldCheckTheMessageCountOfTheQueue() throws IOException, InterruptedException {
        assertThat(TestUtils.getMessageCount(rabbitAdmin, queueComplexJson)).isEqualTo(0);
        customerScheduler.runCronJob();
        assertThat(TestUtils.getMessageCount(rabbitAdmin, queueComplexJson)).isEqualTo(1);
    }

    @Test
    public void shouldCheckTheContentOfTheQueue() throws IOException, InterruptedException {
        assertThat(TestUtils.getMessageCount(rabbitAdmin, queueComplexJson)).isEqualTo(0);
        customerScheduler.runCronJob();
        String json = sampleComplexJSON.getContentAsString(StandardCharsets.UTF_8);
        assertThat(TestUtils.getMessageContent(rabbitTemplate, queueComplexJson)).isEqualTo(json);
    }
}