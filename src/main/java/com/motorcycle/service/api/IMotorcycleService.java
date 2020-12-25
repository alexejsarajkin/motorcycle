package com.motorcycle.service.api;

import com.motorcycle.db.datamodel.MotorcycleEntity;
import com.motorcycle.endpoint.rest.dto.MotorcycleCreateDTO;

import java.util.List;

public interface IMotorcycleService {
    List<MotorcycleEntity> retrieveAllMotorcycle();

    MotorcycleEntity retrieveMotorcycleByBrand(String brand);

    MotorcycleEntity createMotorcycle(MotorcycleCreateDTO motorcycleCreateDTO);
}
