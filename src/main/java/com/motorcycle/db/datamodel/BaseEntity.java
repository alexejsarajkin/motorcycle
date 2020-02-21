package com.motorcycle.db.datamodel;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

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
