package com.pallabi.scheduler.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Customer {
    private Long orderId;

    private String name;

    private String email;

    private String postCode;

    private String fullAddress;
}
