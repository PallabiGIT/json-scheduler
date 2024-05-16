package com.pallabi.scheduler.receiver.service;

import com.pallabi.scheduler.utils.model.Customer;
import com.pallabi.scheduler.utils.model.dto.CustomerDTO;
import com.pallabi.scheduler.utils.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQCustomerListenerService {
    @Autowired
    private CustomerService customerService;

    @RabbitListener(queues = {"${rabbit.mq.queue.customer}"})
    public void onCustomerArrival(Customer customer) {
        CustomerDTO customerDTO = getCustomerDetails(customer);
        log.info("Received customer details from queue");
        customerService.addCustomer(customerDTO);
    }

    private CustomerDTO getCustomerDetails(Customer customer) {
        return CustomerDTO
                .builder()
                .orderId(customer.getOrderId())
                .name(customer.getName())
                .email(customer.getEmail())
                .postCode(customer.getPostCode())
                .fullAddress(customer.getFullAddress())
                .build();
    }
}
