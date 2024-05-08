package com.pallabi.scheduler.model.dto;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BackJson")
public class BackJsonDTO {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Lob
    @ToString.Exclude
    @Column(name = "backup_json", columnDefinition="BLOB")
    private byte[] backUpJSON;

    @Column(name = "insertion_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Timestamp insertionTime;
}
