package com.pallabi.scheduler.api.controller;

import com.pallabi.scheduler.api.SchedulerApiApplication;
import com.pallabi.scheduler.api.config.APIConfig;
import com.pallabi.scheduler.utils.model.dto.CustomerDTO;
import com.pallabi.scheduler.utils.repository.CustomerRepository;
import com.pallabi.scheduler.utils.service.CustomerService;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerControllerTest {

    private static final String BASE_PATH = "http://localhost:8080";
    private static final String GET_PATH = "/customers";
    private static final String GET_BY_ID_PATH = "/customer/{orderId}";

    @Autowired
    private CustomerService customerService;

    @Test
    public void shouldReturnCustomerNotFoundError() {
        var response = RestAssured.given().auth().preemptive().basic("user", "user@123").
                get(BASE_PATH + GET_BY_ID_PATH, 12)
                .then()
                .statusCode(404)
                .extract()
                .asString();
        Assertions.assertThat(response).isEqualTo("Customer not found");
    }

    @Test
    public void shouldReturnACustomer() {
                var customerDTO = CustomerDTO
                .builder()
                .orderId(1L)
                .name("TEST_NAME")
                .email("abc@gmail.com")
                .postCode("rt45yu")
                .fullAddress("2, champion way,rt56yu")
                .build();
        customerService.addCustomer(customerDTO);

        var response = RestAssured.given().auth().preemptive().basic("user", "user@123").
                get(BASE_PATH + GET_BY_ID_PATH, 1)
                .then()
                .statusCode(200)
                .extract()
                .as(CustomerDTO.class);
        Assertions.assertThat(response.getName()).isEqualTo("TEST_NAME");
        Assertions.assertThat(response.getEmail()).isEqualTo("abc@gmail.com");
        Assertions.assertThat(response.getPostCode()).isEqualTo("rt45yu");
        Assertions.assertThat(response.getFullAddress()).isEqualTo("2, champion way,rt56yu");
        Assertions.assertThat(response.getInsertionTime()).isNotNull();

    }

    @Test
    public void shouldReturnBadRequestError() {
        RestAssured.given().auth().preemptive().basic("user", "user@123").
                get(BASE_PATH + GET_BY_ID_PATH, "rr")
                .then()
                .statusCode(400);

    }

    @Test
    public void shouldReturnUnauthorizedWithInvalidUsername() {
        RestAssured.given().auth().preemptive().basic("user1", "user@123").
                get(BASE_PATH + GET_BY_ID_PATH, "12")
                .then()
                .statusCode(401);

    }
    @Test
    public void shouldReturnUnauthorizedWithInvalidPassword() {
        RestAssured.given().auth().preemptive().basic("user", "user@1234").
                get(BASE_PATH + GET_BY_ID_PATH, "12")
                .then()
                .statusCode(401);

    }
    @Test
    public void shouldReturnUnauthorizedEmptyUsernamePassword() {
        RestAssured.given().auth().preemptive().basic("", "").
                get(BASE_PATH + GET_BY_ID_PATH, "12")
                .then()
                .statusCode(401);

    }
}
