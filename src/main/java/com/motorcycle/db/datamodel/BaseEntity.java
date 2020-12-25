package com.motorcycle.db.datamodel;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.OffsetDateTime;

@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreatedDate
    @Column(name = "created", columnDefinition = "timestamp default current_timestamp", nullable = false)
    private OffsetDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(30) default 'ACTIVE'", nullable = false)
    private Status status;
}
