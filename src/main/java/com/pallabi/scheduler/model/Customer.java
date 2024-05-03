package com.pallabi.scheduler.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Builder
public class Customer {
    private Long orderId;

    private String name;

    private String email;

    private String postCode;

    private String fullAddress;

    private Timestamp insertionTime;
}
