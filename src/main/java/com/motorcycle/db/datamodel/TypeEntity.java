package com.motorcycle.db.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "type")
public class TypeEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
}
