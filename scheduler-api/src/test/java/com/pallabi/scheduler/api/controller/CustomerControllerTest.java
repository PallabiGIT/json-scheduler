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

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void shouldReturnMultipleCustomersSuccessfully() {
        var customerDTO_1 = CustomerDTO
                .builder()
                .orderId(1L)
                .name("TEST_NAME")
                .email("abc@gmail.com")
                .postCode("rt45yu")
                .fullAddress("2, champion way,rt56yu")
                .build();

        var customerDTO_2 = CustomerDTO
                .builder()
                .orderId(2L)
                .name("TEST_NUNU")
                .email("abcd@gmail.com")
                .postCode("rt46yu")
                .fullAddress("8, champion way,rt46yu")
                .build();
        customerService.addCustomer(customerDTO_1);
        customerService.addCustomer(customerDTO_2);

        var response = RestAssured.given().auth().preemptive().basic("user", "user@123").
                get(BASE_PATH + GET_PATH)
                .then()
                .statusCode(200)
                .extract()
                .as(CustomerDTO[].class);
        assertThat(response.length).isEqualTo(2);
        CustomerDTO customerDTOResponse_1 = response[0];
        CustomerDTO customerDTOResponse_2 = response[1];
        assertThat(customerDTOResponse_1.getOrderId()).isEqualTo(customerDTO_1.getOrderId());
        assertThat(customerDTOResponse_1.getName()).isEqualTo(customerDTO_1.getName());
        assertThat(customerDTOResponse_1.getEmail()).isEqualTo(customerDTO_1.getEmail());
        assertThat(customerDTOResponse_1.getPostCode()).isEqualTo(customerDTO_1.getPostCode());
        assertThat(customerDTOResponse_1.getFullAddress()).isEqualTo(customerDTO_1.getFullAddress());
        assertThat(customerDTOResponse_2.getOrderId()).isEqualTo(customerDTO_2.getOrderId());
        assertThat(customerDTOResponse_2.getName()).isEqualTo(customerDTO_2.getName());
        assertThat(customerDTOResponse_2.getEmail()).isEqualTo(customerDTO_2.getEmail());
        assertThat(customerDTOResponse_2.getPostCode()).isEqualTo(customerDTO_2.getPostCode());
        assertThat(customerDTOResponse_2.getFullAddress()).isEqualTo(customerDTO_2.getFullAddress());





    }


    @Test
    public void shouldReturnUnauthorizedWithInvalidUsernameForCustomers() {
        RestAssured.given().auth().preemptive().basic("user1", "user@123").
                get(BASE_PATH + GET_PATH)
                .then()
                .statusCode(401);

    }
    @Test
    public void shouldReturnUnauthorizedWithInvalidPasswordForCustomers() {
        RestAssured.given().auth().preemptive().basic("user", "user@1234").
                get(BASE_PATH + GET_PATH)
                .then()
                .statusCode(401);

    }
    @Test
    public void shouldReturnUnauthorizedEmptyUsernamePasswordForCustomers() {
        RestAssured.given().auth().preemptive().basic("", "").
                get(BASE_PATH + GET_PATH)
                .then()
                .statusCode(401);

    }

}

