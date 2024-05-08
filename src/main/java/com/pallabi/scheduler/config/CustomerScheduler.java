package com.pallabi.scheduler.config;

import com.google.gson.Gson;
import com.pallabi.scheduler.model.dto.CustomerDTO;
import com.pallabi.scheduler.model.generated.Address;
import com.pallabi.scheduler.model.generated.ComplexObject;
import com.pallabi.scheduler.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class CustomerScheduler {

    @Value("${customer.cron.location}")
    private String userJOSNFilePath;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbit.mq.queue.complex.json}")
    private String queueComplexJson;

    @Scheduled(cron = "${cron.scheduler}")
    public void runCronJob() throws IOException {
        try {
            Resource fileSystemResource = resourceLoader.getResource(userJOSNFilePath);
            String json = fileSystemResource.getContentAsString(StandardCharsets.UTF_8);
            log.info("Sending complex JSON to queue {}", queueComplexJson);
            rabbitTemplate.convertAndSend("", queueComplexJson, json);
            log.info("Cron JOB RAN SUCCESSFULLY");
            deleteFileAfterProcessing();
        }
        catch(FileNotFoundException e){
            log.warn("NO Files found to process");
        }
        catch (RuntimeException e){
            log.error("Error while running cron JOB");
            throw e;
        }
    }

    protected void deleteFileAfterProcessing() throws IOException {
        Resource fileSystemResource = resourceLoader.getResource(userJOSNFilePath);
        if (fileSystemResource.getFile().delete()) {
            log.info("Deleted the file: " + fileSystemResource.getFile().getName());
        } else {
           log.warn("Failed to delete the file." + fileSystemResource.getFile().getName());
        }
    }
}