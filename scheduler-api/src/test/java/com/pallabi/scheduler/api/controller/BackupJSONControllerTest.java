package com.pallabi.scheduler.api.controller;

import com.pallabi.scheduler.utils.model.dto.BackJsonDTO;
import com.pallabi.scheduler.utils.repository.BackJSONRepository;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BackupJSONControllerTest {

    private static final String BASE_PATH = "http://localhost:8080";
    private static final String GET_PATH = "/backUPJSON/count";
    private static final String GET_BY_ID_PATH = "/backUPJSON/{date}";

    @Autowired
    private BackJSONRepository backJSONRepository;

    @Value("classpath:complex_input.json")
    Resource sampleBackUpJsonFile;

    @Test
    public void shouldReturnBackUpJsonCountAsZero() {
        deleteBackUpJSONData();
        var response = RestAssured.given().auth().preemptive().basic("user", "user@123").
                get(BASE_PATH + GET_PATH).
                then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        assertThat(response).isEqualTo("Count is - 0");
    }

    @Test
    public void shouldReturnABackUpJson() throws IOException {
        insertBackUpJSONData();
        var response = RestAssured.given().auth().preemptive().basic("user", "user@123").
                get(BASE_PATH + GET_PATH).
                then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        assertThat(response).isEqualTo("Count is - 1");
        deleteBackUpJSONData();
    }

    @Test
    public void shouldReturnUnauthorizedWithInvalidUsernameForBackUpJsonCount() {
        RestAssured.given().auth().preemptive().basic("user1", "user@123").
                get(BASE_PATH + GET_PATH)
                .then()
                .statusCode(401);

    }

    @Test
    public void shouldReturnUnauthorizedWithInvalidPasswordForBackUpJsonCount() {
        RestAssured.given().auth().preemptive().basic("user", "user@1234").
                get(BASE_PATH + GET_PATH)
                .then()
                .statusCode(401);

    }

    @Test
    public void shouldReturnUnauthorizedWithEmptyUsernamePassword() {
        RestAssured.given().auth().preemptive().basic("", "").
                get(BASE_PATH + GET_PATH)
                .then()
                .statusCode(401);
    }

    @Test
    public void shouldReturnBackUpJsonByDate() throws IOException {
        insertBackUpJSONData();
        var response = RestAssured.given().auth().preemptive().basic("user", "user@123").
                get(BASE_PATH + GET_BY_ID_PATH, getTodayDate())
                .then()
                .statusCode(200)
                .extract().asString();
        Assertions.assertThat(response).isEqualTo(sampleBackUpJsonFile.getContentAsString(StandardCharsets.UTF_8));
        deleteBackUpJSONData();
    }

    @Test
    public void shouldNotReturnBackUpJsonAsTheFindByDateReturnsNoData(){
        var response = RestAssured.given().auth().preemptive().basic("user", "user@123").
                get(BASE_PATH + GET_BY_ID_PATH, "2024-06-10")
                .then()
                .statusCode(404)
                .extract().asString();
        Assertions.assertThat(response).isEqualTo("Backup JSON not found for the date Mon Jun 10 00:00:00 BST 2024");
    }

    @Test
    public void shouldReturnUnauthorizedWithInvalidUsernameForFindBackUpJsonByDate() {
        RestAssured.given().auth().preemptive().basic("user1", "user@123").
                get(BASE_PATH + GET_BY_ID_PATH, "2024-06-19")
                .then()
                .statusCode(401);
    }

    @Test
    public void shouldReturnUnauthorizedWithInvalidPasswordForFindBackUpJsonByDate() {
        RestAssured.given().auth().preemptive().basic("user", "user@1234").
                get(BASE_PATH + GET_BY_ID_PATH, "2024-06-19")
                .then()
                .statusCode(401);

    }

    @Test
    public void shouldReturnUnauthorizedEmptyUsernamePasswordForFindBackUpJsonByDate() {
        RestAssured.given().auth().preemptive().basic("", "").
                get(BASE_PATH + GET_BY_ID_PATH, "12")
                .then()
                .statusCode(401);
    }

    private void insertBackUpJSONData() throws IOException {
        BackJsonDTO backUpJsonDTO = BackJsonDTO
                .builder()
                .backUpJSON(sampleBackUpJsonFile.getContentAsByteArray())
                .build();
        backJSONRepository.save(backUpJsonDTO);
    }

    private void deleteBackUpJSONData() {
        backJSONRepository.deleteAll();
    }

    private String getTodayDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate);
    }
}
