package com.motorcycle.service.impl;

import com.motorcycle.db.datamodel.MotorcycleEntity;
import com.motorcycle.db.datamodel.Status;
import com.motorcycle.db.datamodel.TypeEntity;
import com.motorcycle.db.repository.api.IMotorcycleRepository;
import com.motorcycle.db.repository.api.ITypeRepository;
import com.motorcycle.endpoint.rest.dto.MotorcycleCreateDTO;
import com.motorcycle.exception.NotFoundException;
import com.motorcycle.service.api.IMotorcycleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MotorcycleService implements IMotorcycleService {

    private final IMotorcycleRepository motorcycleRepository;
    private final ITypeRepository typeRepository;

    @Override
    public List<MotorcycleEntity> retrieveAllMotorcycle() {
        return motorcycleRepository.findAll();
    }

    @Override
    public MotorcycleEntity retrieveMotorcycleByBrand(String brand) {
        return motorcycleRepository.findByBrandEntity(brand);
    }

    @Override
    public MotorcycleEntity createMotorcycle(MotorcycleCreateDTO motorcycleCreateDTO) {
        TypeEntity typeEntity = typeRepository.findByName(motorcycleCreateDTO.getTypeName());

        if (typeEntity == null) {
            throw new NotFoundException("Type not found (" + motorcycleCreateDTO.getTypeName() + ")");
        }

        MotorcycleEntity motorcycleEntity = new MotorcycleEntity();
        motorcycleEntity.setCreated(OffsetDateTime.now());
        motorcycleEntity.setStatus(Status.ACTIVE);
        motorcycleEntity.setBrandEntity(motorcycleCreateDTO.getBrand());
        motorcycleEntity.setModel(motorcycleCreateDTO.getModel());
        motorcycleEntity.setType(typeEntity);
        return motorcycleRepository.save(motorcycleEntity);
    }
}
