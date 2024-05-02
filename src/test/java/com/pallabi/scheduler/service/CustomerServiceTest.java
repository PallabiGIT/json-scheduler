package com.pallabi.scheduler.service;


import com.pallabi.scheduler.model.dto.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = "spring.config.location=classpath:/test-application.properties")
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Value("classpath:complex_input.json")
    Resource sampleComplexJSON;

    @Test
    public void shouldAddAndGetCustomerData() throws IOException {
        File file = sampleComplexJSON.getFile();
        byte[] testFile = Files.readAllBytes(file.toPath());

        //TESTS for adding one customer
        CustomerDTO customerDTO = CustomerDTO
                .builder()
                .orderId(1L)
                .name("SWAROOP")
                .email("Swaroop@gmail.com")
                .postCode("gu526ht")
                .fullAddress("2, TEST ADDRESS, TEST_POSTCODE")
                .backUpJSON(testFile)
                .build();
        customerService.addCustomer(customerDTO);

        //TEST for get customer details by customer ID
        customerService.getByOrderID(1L).ifPresent(customerDTO1 -> {
            assertThat(customerDTO1.getName()).isEqualTo("SWAROOP");
            assertThat(customerDTO1.getEmail()).isEqualTo("Swaroop@gmail.com");
            assertThat(customerDTO1.getPostCode()).isEqualTo("gu526ht");
            assertThat(customerDTO1.getFullAddress()).isEqualTo("2, TEST ADDRESS, TEST_POSTCODE");
            assertThat(customerDTO1.getBackUpJSON()).isEqualTo(testFile);
        });

        //TEST for get customer details by ID
        customerService.getByID(1L).ifPresent(customerDTO1 -> {
            assertThat(customerDTO1.getName()).isEqualTo("SWAROOP");
            assertThat(customerDTO1.getEmail()).isEqualTo("Swaroop@gmail.com");
            assertThat(customerDTO1.getPostCode()).isEqualTo("gu526ht");
            assertThat(customerDTO1.getFullAddress()).isEqualTo("2, TEST ADDRESS, TEST_POSTCODE");
            assertThat(customerDTO1.getBackUpJSON()).isEqualTo(testFile);
        });

        //TEST for get all customer details
        assertThat(customerService.getAllCustomer().spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }
}