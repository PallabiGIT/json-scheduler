package com.pallabi.scheduler.utils.service;

import com.pallabi.scheduler.utils.model.dto.CustomerDTO;
import com.pallabi.scheduler.utils.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
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
        log.info("GET All customer DB call triggered");
        return customerRepository.findAll();
    }

    public Optional<CustomerDTO> getByID(long id) {
        log.info("GET customer by ID DB call triggered for ID - " + id);
        return customerRepository.findById(id);
    }

    public Optional<CustomerDTO> getByOrderID(long orderID) {
        log.info("GET customer by customer ID DB call triggered for orderID = " + orderID);
        return StreamSupport
                .stream(customerRepository.findAll().spliterator(), false)
                .filter(customerDTO -> customerDTO.getOrderId().equals(orderID))
                .findFirst();
    }

    public void addCustomer(CustomerDTO customerDTO) {
        try {
            customerRepository.save(customerDTO);
            log.info("Customer added successfully" + customerDTO);
        } catch (Exception e) {
            if (/**e instanceof ConstraintViolationException ||**/
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