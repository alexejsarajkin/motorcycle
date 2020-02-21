package com.motorcycle.service.api;

import java.util.List;

import com.motorcycle.db.datamodel.MotorcycleEntity;
import com.motorcycle.endpoint.rest.dto.MotorcycleCreateDTO;

public interface IMotorcycleService {
  List<MotorcycleEntity> retrieveAllMotorcycle();

  MotorcycleEntity retrieveMotorcycleByBrand(String brand);

  MotorcycleEntity createMotorcycle(MotorcycleCreateDTO motorcycleCreateDTO);
}
