package com.pallabi.scheduler.config;

import com.google.gson.Gson;
import com.pallabi.scheduler.CustomerException;
import com.pallabi.scheduler.model.dto.CustomerDTO;
import com.pallabi.scheduler.model.generated.Address;
import com.pallabi.scheduler.model.generated.ComplexObject;
import com.pallabi.scheduler.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    private CustomerService customerService;

    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron= "0/10 * * ? * *")
    public void runCronJob() throws IOException {
        try {
            Resource fileSystemResource = resourceLoader.getResource(userJOSNFilePath);
            String json = fileSystemResource.getContentAsString(StandardCharsets.UTF_8);
            List<ComplexObject> complexObjects = getObjectFromJsonFile(json, ComplexObject.class);
            complexObjects.forEach(complexObject -> {
                CustomerDTO customerDTO = getCustomerDetails(complexObject, json.getBytes());
                customerService.addCustomer(customerDTO);
            });
            log.info("Cron JOB RAN SUCCESSFULLY");
        } catch(RuntimeException e){
            log.error("Error while running cron JOB");
            throw e;
        }
    }

    private CustomerDTO getCustomerDetails(ComplexObject complexObject, byte[] json) {
        Address address = complexObject.getClient().getAddress();
        String fullAddress =  address.getHouseNumber() +
                ", " +
                address.getStreetName() +
                ", " +
                address.getCity() +
                ", " +
                address.getPostalCode() +
                ", " +
                address.getCountry();
        return CustomerDTO
                .builder()
                .orderId(Long.valueOf(complexObject.getOrderID()))
                .name(complexObject.getName())
                .email(complexObject.getEmail())
                .postCode(address.getPostalCode())
                .fullAddress(fullAddress)
                .backUpJSON(json)
                .build();
    }

    private List<ComplexObject> getObjectFromJsonFile(String jsonData, Class classObject) {
        Gson gson = new Gson();
        return Arrays.asList(gson.fromJson(jsonData, ComplexObject[].class));
    }


}