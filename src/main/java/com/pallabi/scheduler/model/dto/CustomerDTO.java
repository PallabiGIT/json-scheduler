package com.pallabi.scheduler.model.dto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

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

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="order_iD", nullable=false, unique=true)
    private Long orderId;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="email", nullable=false)
    private String email;

    @Column(name="post_code", nullable=false)
    private String postCode;

    @Column(name="full_address", nullable=false)
    private String fullAddress;

    @Lob
    @Column(name = "backup_json", columnDefinition="BLOB")
    private byte[] backUpJSON;

    @Column(name="insertion_time")
    private Timestamp insertionTime;
}
