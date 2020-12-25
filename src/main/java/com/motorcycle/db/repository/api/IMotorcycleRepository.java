package com.motorcycle.db.repository.api;

import com.motorcycle.db.datamodel.MotorcycleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMotorcycleRepository extends JpaRepository<MotorcycleEntity, Integer> {
    MotorcycleEntity findByBrandEntity(String brand);
}
