package com.motorcycle.db.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motorcycle.db.datamodel.MotorcycleEntity;

public interface IMotorcycleRepository extends JpaRepository<MotorcycleEntity, Integer> {
  MotorcycleEntity findByBrandEntity(String brand);
}
