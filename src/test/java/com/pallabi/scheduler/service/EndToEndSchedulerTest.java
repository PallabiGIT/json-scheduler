package com.pallabi.scheduler;


import com.pallabi.scheduler.model.dto.CustomerDTO;
import com.pallabi.scheduler.repository.BackJSONRepository;
import com.pallabi.scheduler.repository.CustomerRepository;
import com.pallabi.scheduler.service.BackUpJSONService;
import com.pallabi.scheduler.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestCustomerScheduler {
    @Autowired
    private CustomerScheduler customerScheduler;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BackUpJSONService backUpJSONService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BackJSONRepository backJSONRepository;

    @Value("classpath:complex_input.json")
    private Resource sampleComplexJSON;

    @Value("${customer.cron.file.location}")
    private String cronFileDestinationLocation;

    @Value("${rabbit.mq.queue.customer}")
    private String queueCustomer;

    @Value("${rabbit.mq.queue.complex.json}")
    private String queueComplexJson;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @BeforeEach
    public void setup() throws IOException {
        File src = sampleComplexJSON.getFile();
        File dest = new File(cronFileDestinationLocation);
        if(!dest.exists()) {
            FileSystemUtils.copyRecursively(src, dest);
        }
        customerRepository.deleteAll();
        backJSONRepository.deleteAll();

        rabbitAdmin.purgeQueue(queueCustomer, false);
        rabbitAdmin.purgeQueue(queueComplexJson, false);
    }

    @Test
    public void shouldReadTheJSONAndInsertANDReadFromDBSuccessfully() throws IOException, InterruptedException {
        assertThat(backUpJSONService.getBackupJSONCount()).isEqualTo(0);
        assertThat(customerService.getAllCustomer().spliterator().getExactSizeIfKnown()).isEqualTo(0);

        customerScheduler.runCronJob();

        File file = sampleComplexJSON.getFile();
//        byte[] expectedJSONINByteArray = Files.readAllBytes(file.toPath());

        Thread.sleep(10000);

        //TEST for get customer details by customer ID
        CustomerDTO customerDTO1 = customerService.getByOrderID(10000220).orElseThrow(() -> new RuntimeException("customer not found"));
        assertThat(customerDTO1.getName()).isEqualTo("John Doe");
        assertThat(customerDTO1.getEmail()).isEqualTo("john.doe@gmail.com");
        assertThat(customerDTO1.getPostCode()).isEqualTo("999");
        assertThat(customerDTO1.getFullAddress()).isEqualTo("22, streename, CityName, 999, CountryCode");

        //TEST for get customer details by customer ID
        CustomerDTO customerDTO3 = customerService.getByOrderID(10000303).orElseThrow(() -> new RuntimeException("customer not found"));
        assertThat(customerDTO3.getName()).isEqualTo("Gulu Bandia");
        assertThat(customerDTO3.getEmail()).isEqualTo("gulu.bandia@gmail.com");
        assertThat(customerDTO3.getPostCode()).isEqualTo("gu526ht");
        assertThat(customerDTO3.getFullAddress()).isEqualTo("2, Champion way, Fleet, gu526ht, GB");

        assertThat(customerService.getAllCustomer().spliterator().getExactSizeIfKnown()).isEqualTo(2);
        assertThat(backUpJSONService.getBackupJSONCount()).isEqualTo(1);
    }
}