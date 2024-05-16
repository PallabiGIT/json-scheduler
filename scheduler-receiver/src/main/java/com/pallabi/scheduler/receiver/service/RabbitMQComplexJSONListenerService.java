package com.pallabi.scheduler.receiver.service;

import com.google.gson.Gson;
import com.pallabi.scheduler.utils.model.Customer;
import com.pallabi.scheduler.utils.model.dto.BackJsonDTO;
import com.pallabi.scheduler.utils.model.generated.Address;
import com.pallabi.scheduler.utils.model.generated.ComplexObject;
import com.pallabi.scheduler.utils.service.BackUpJSONService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class RabbitMQComplexJSONListenerService {
    @Autowired
    private BackUpJSONService backUpJSONService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbit.mq.queue.customer}")
    private String queueCustomer;

    @RabbitListener(queues = {"${rabbit.mq.queue.complex.json}"})
    public void onCustomerArrival(String complexJSON) {
        log.info("Received Complex json from queue");
        backUpJSONService.backUpJSON(getBackJSONDTO(complexJSON));

        List<ComplexObject> complexObjects = getObjectFromJsonFile(complexJSON, ComplexObject.class);
        complexObjects.forEach(complexObject -> {
            log.info("Sending customer details to queue");
            Customer customer = getCustomerDetails(complexObject);
            rabbitTemplate.convertAndSend("", queueCustomer, customer);
        });
    }

    private Customer getCustomerDetails(ComplexObject complexObject) {
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
        return Customer
                .builder()
                .orderId(Long.valueOf(complexObject.getOrderID()))
                .name(complexObject.getName())
                .email(complexObject.getEmail())
                .postCode(address.getPostalCode())
                .fullAddress(fullAddress)
                .build();
    }

    private BackJsonDTO getBackJSONDTO(String complexJSON){
        return BackJsonDTO
                .builder()
                .backUpJSON(complexJSON.getBytes())
                .build();
    }

    private List<ComplexObject> getObjectFromJsonFile(String jsonData, Class classObject) {
        Gson gson = new Gson();
        return Arrays.asList(gson.fromJson(jsonData, ComplexObject[].class));
    }
}
