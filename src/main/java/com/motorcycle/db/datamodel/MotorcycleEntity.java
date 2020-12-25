package com.motorcycle.db.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
