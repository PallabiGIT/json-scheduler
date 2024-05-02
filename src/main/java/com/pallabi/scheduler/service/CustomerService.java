package com.pallabi.scheduler.service;

import com.pallabi.scheduler.model.dto.CustomerDTO;
import com.pallabi.scheduler.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Iterable<CustomerDTO> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Optional<CustomerDTO> getByID(long id) {
        return customerRepository.findById(id);
    }

    public Optional<CustomerDTO> getByOrderID(long orderID) {
        return StreamSupport
                .stream(customerRepository.findAll().spliterator(), false)
                .filter(customerDTO -> customerDTO.getOrderId().equals(orderID))
                .findFirst();
    }

    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(customerDTO);
    }
}