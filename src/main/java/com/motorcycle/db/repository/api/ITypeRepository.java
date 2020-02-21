package com.motorcycle.db.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motorcycle.db.datamodel.TypeEntity;

public interface ITypeRepository extends JpaRepository<TypeEntity, Integer> {
  TypeEntity findByName(String name);
}
