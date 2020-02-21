package com.motorcycle.db.datamodel;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "motorcycle")
public class MotorcycleEntity extends BaseEntity {
  @NotNull
  private String brandEntity;

  @NotNull
  private String model;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "type_id", referencedColumnName = "id")
  @Fetch(FetchMode.JOIN)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @NotNull
  private TypeEntity type;
}
