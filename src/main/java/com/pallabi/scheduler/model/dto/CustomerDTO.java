package com.pallabi.scheduler.model.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Customer")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDTO {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="orderID", nullable=false, unique=true)
    private Long orderId;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="email", nullable=false)
    private String email;

    @Column(name="postCode", nullable=false)
    private String postCode;

    @Column(name="fullAddress", nullable=false)
    private String fullAddress;

    @Lob
    @Column(name = "backup_json", columnDefinition="BLOB")
    private byte[] backUpJSON;
}
