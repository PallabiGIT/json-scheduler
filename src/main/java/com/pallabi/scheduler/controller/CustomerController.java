package com.pallabi.scheduler.controller;

import com.pallabi.scheduler.model.Customer;
import com.pallabi.scheduler.model.dto.CustomerDTO;
import com.pallabi.scheduler.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController()
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // Select, Insert, Delete, Update Operations for an Employee

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<Customer> getCustomers(){
        List<CustomerDTO> customerDTOS =  StreamSupport.stream(customerService.getAllCustomer().spliterator(), false)
                .collect(Collectors.toList());
        return customerDTOS.stream().map(customerDTO ->
                        Customer.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .insertionTime(customerDTO.getInsertionTime())
                .postCode(customerDTO.getPostCode())
                .orderId(customerDTO.getOrderId())
                .fullAddress(customerDTO.getFullAddress())
                .build())
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/customer/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployee(@PathVariable Integer orderId){
        Optional<CustomerDTO> customerDTOOptional = customerService.getByOrderID(orderId);
        if(customerDTOOptional.isPresent()){
            CustomerDTO customerDTO = customerDTOOptional.get();
            Customer customer = Customer.builder()
                    .name(customerDTO.getName())
                    .email(customerDTO.getEmail())
                    .insertionTime(customerDTO.getInsertionTime())
                    .postCode(customerDTO.getPostCode())
                    .orderId(customerDTO.getOrderId())
                    .fullAddress(customerDTO.getFullAddress())
                    .build();
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }


    }
}
