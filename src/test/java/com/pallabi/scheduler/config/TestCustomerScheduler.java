package com.pallabi.scheduler.config;


import com.pallabi.scheduler.model.dto.CustomerDTO;
import com.pallabi.scheduler.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestCustomerScheduler {
    @Autowired
    private CustomerScheduler customerScheduler;

    @Autowired
    private CustomerService customerService;

    @Value("classpath:complex_input.json")
    private Resource sampleComplexJSON;

    @Value("${customer.cron.file.location}")
    private String cronFileDestinationLocation;

    @BeforeEach
    public void setup() throws IOException {
        File src = sampleComplexJSON.getFile();
        File dest = new File(cronFileDestinationLocation);
        if(!Files.exists(Paths.get(cronFileDestinationLocation))) {
            FileSystemUtils.copyRecursively(src, dest);
        }
    }


    @Test
    public void shouldReadTheJSONAndInsertANDReadFromDBSuccessfully() throws IOException {
        customerScheduler.runCronJob();

        File file = sampleComplexJSON.getFile();
        byte[] testFile = Files.readAllBytes(file.toPath());

        //TEST for get customer details by customer ID
        CustomerDTO customerDTO1 = customerService.getByOrderID(10000220).orElseThrow(() -> new RuntimeException("customer not found"));
        assertThat(customerDTO1.getName()).isEqualTo("John Doe");
        assertThat(customerDTO1.getEmail()).isEqualTo("john.doe@gmail.com");
        assertThat(customerDTO1.getPostCode()).isEqualTo("999");
        assertThat(customerDTO1.getFullAddress()).isEqualTo("22, streename, CityName, 999, CountryCode");
        assertThat(customerDTO1.getBackUpJSON()).isEqualTo(testFile);

        //TEST for get customer details by ID
        CustomerDTO customerDTO2 = customerService.getByID(1L).orElseThrow(() -> new RuntimeException("customer not found"));
        assertThat(customerDTO2.getName()).isEqualTo("John Doe");
        assertThat(customerDTO2.getEmail()).isEqualTo("john.doe@gmail.com");
        assertThat(customerDTO2.getPostCode()).isEqualTo("999");
        assertThat(customerDTO2.getFullAddress()).isEqualTo("22, streename, CityName, 999, CountryCode");
        assertThat(customerDTO2.getBackUpJSON()).isEqualTo(testFile);

        //TEST for get customer details by customer ID
        CustomerDTO customerDTO3 = customerService.getByOrderID(10000303).orElseThrow(() -> new RuntimeException("customer not found"));
        assertThat(customerDTO3.getName()).isEqualTo("Gulu Bandia");
        assertThat(customerDTO3.getEmail()).isEqualTo("gulu.bandia@gmail.com");
        assertThat(customerDTO3.getPostCode()).isEqualTo("gu526ht");
        assertThat(customerDTO3.getFullAddress()).isEqualTo("2, Champion way, Fleet, gu526ht, GB");
        assertThat(customerDTO3.getBackUpJSON()).isEqualTo(testFile);

        //TEST for get customer details by ID
        CustomerDTO customerDTO4 = customerService.getByID(2L).orElseThrow(() -> new RuntimeException("customer not found"));
        assertThat(customerDTO4.getName()).isEqualTo("Gulu Bandia");
        assertThat(customerDTO4.getEmail()).isEqualTo("gulu.bandia@gmail.com");
        assertThat(customerDTO4.getPostCode()).isEqualTo("gu526ht");
        assertThat(customerDTO4.getFullAddress()).isEqualTo("2, Champion way, Fleet, gu526ht, GB");
        assertThat(customerDTO4.getBackUpJSON()).isEqualTo(testFile);

        //TEST for get all customer details
        assertThat(customerService.getAllCustomer().spliterator().getExactSizeIfKnown()).isEqualTo(2);
    }
}
