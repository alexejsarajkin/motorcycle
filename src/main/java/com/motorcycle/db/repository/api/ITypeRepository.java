package com.motorcycle.db.repository.api;

import com.motorcycle.db.datamodel.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITypeRepository extends JpaRepository<TypeEntity, Integer> {
    TypeEntity findByName(String name);
}
