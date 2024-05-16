package com.pallabi.scheduler.utils.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Customer implements Serializable {
    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("postCode")
    private String postCode;

    @JsonProperty("fullAddress")
    private String fullAddress;

    @JsonProperty("insertionTime")
    private Timestamp insertionTime;
}
