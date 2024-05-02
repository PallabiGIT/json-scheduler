package com.pallabi.scheduler.service;

import com.pallabi.scheduler.CustomerException;
import com.pallabi.scheduler.model.dto.CustomerDTO;
import com.pallabi.scheduler.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
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

    public void addCustomer(CustomerDTO customerDTO) {
        try {
            customerRepository.save(customerDTO);
        } catch (Exception e) {
            if (e instanceof ConstraintViolationException ||
                    e instanceof SQLIntegrityConstraintViolationException ||
                    e instanceof DataIntegrityViolationException) {
                log.warn("Error while adding duplicate customer", e);
            }
            else{
                throw e;
            }
        }
    }
}