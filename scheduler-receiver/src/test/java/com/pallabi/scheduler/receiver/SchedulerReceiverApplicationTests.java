package com.pallabi.scheduler.receiver;

import com.pallabi.scheduler.receiver.config.ReceiverConfig;
import com.pallabi.scheduler.receiver.config.TestConfig;
import com.pallabi.scheduler.utils.model.dto.CustomerDTO;
import com.pallabi.scheduler.utils.service.BackUpJSONService;
import com.pallabi.scheduler.utils.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Import(TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SchedulerReceiverApplicationTests {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private BackUpJSONService backUpJSONService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("classpath:complex_input.json")
    private Resource sampleComplexJSON;

    @Value("${rabbit.mq.queue.complex.json}")
    private String queueComplexJson;

    private String jsonInTheTest = null;

    @BeforeEach
    public void setup() throws IOException, InterruptedException {
        jsonInTheTest = sampleComplexJSON.getContentAsString(StandardCharsets.UTF_8);
        rabbitTemplate.convertAndSend("", queueComplexJson, jsonInTheTest);
        Thread.sleep(10000);
    }

    @Test
    public void shouldReadTheJSONAndInsertANDReadFromDBSuccessfully() throws IOException, InterruptedException {
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

        byte[] actualFile = backUpJSONService.getAllBackupJSON().get(0).getBackUpJSON();
        assertThat(
                new String(actualFile, StandardCharsets.UTF_8)).isEqualTo(jsonInTheTest);
    }
}