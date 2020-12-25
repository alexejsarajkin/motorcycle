package com.motorcycle.service.impl;

import com.motorcycle.db.datamodel.MotorcycleEntity;
import com.motorcycle.db.datamodel.Status;
import com.motorcycle.db.datamodel.TypeEntity;
import com.motorcycle.db.repository.api.IMotorcycleRepository;
import com.motorcycle.db.repository.api.ITypeRepository;
import com.motorcycle.endpoint.rest.dto.MotorcycleCreateDTO;
import com.motorcycle.exception.NotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.OffsetDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class MotorcycleServiceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    private MotorcycleService motorcycleService;

    @Mock
    private IMotorcycleRepository motorcycleRepository;

    @Mock
    private ITypeRepository typeRepository;

    @Before
    public void setUp() {
        motorcycleService = new MotorcycleService(motorcycleRepository, typeRepository);
    }

    private MotorcycleCreateDTO createWrongMotorcycleCreateDTO() {
        MotorcycleCreateDTO motorcycleCreateDTO = new MotorcycleCreateDTO();
        motorcycleCreateDTO.setBrand("ResponseEntityExceptionHandlerBrand");
        motorcycleCreateDTO.setModel("ResponseEntityExceptionHandlerModel");
        motorcycleCreateDTO.setTypeName("ResponseEntityExceptionHandlerType");
        return motorcycleCreateDTO;
    }

    private MotorcycleCreateDTO createMotorcycleCreateDTO() {
        MotorcycleCreateDTO motorcycleCreateDTO = new MotorcycleCreateDTO();
        motorcycleCreateDTO.setBrand("HD");
        motorcycleCreateDTO.setModel("Sport glide");
        motorcycleCreateDTO.setTypeName("Cruiser");
        return motorcycleCreateDTO;
    }

    private TypeEntity createTypeEntity() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setName("Cruiser");
        typeEntity.setCreated(OffsetDateTime.now());
        typeEntity.setStatus(Status.ACTIVE);
        return typeEntity;
    }

//  @Test
//  public void createMotorcycle_WrongInputData() throws ValidationException {
//    // Given
//    MotorcycleCreateDTO motorcycleCreateDTO = createWrongMotorcycleCreateDTO();
//
//    // Then
//    exception.expect(ValidationException.class);
//    exception.expectMessage(containsString("Brand is not valid"));
//    exception.expectMessage(containsString("Model is not valid"));
//    exception.expectMessage(containsString("Type is not valid"));
//
//    // When
//    motorcycleService.createMotorcycle(motorcycleCreateDTO);
//
//    // Then
//    verify(motorcycleRepository, never()).save(any(MotorcycleEntity.class));
//  }

    @Test
    public void createMotorcycle_TypeNotFound() throws NotFoundException {
        // Given
        MotorcycleCreateDTO motorcycleCreateDTO = createMotorcycleCreateDTO();
        doReturn(null).when(typeRepository).findByName(motorcycleCreateDTO.getTypeName());

        // Then
        exception.expect(NotFoundException.class);
        exception.expectMessage(containsString("Type not found (" + motorcycleCreateDTO.getTypeName() + ")"));

        // When
        motorcycleService.createMotorcycle(motorcycleCreateDTO);

        // Then
        verify(motorcycleRepository, never()).save(any(MotorcycleEntity.class));
    }


    @Test
    public void createMotorcycle() {
        // Given
        MotorcycleCreateDTO motorcycleCreateDTO = createMotorcycleCreateDTO();
        TypeEntity typeEntity = createTypeEntity();
        doReturn(createTypeEntity()).when(typeRepository).findByName("Cruiser");

        // When
        motorcycleService.createMotorcycle(motorcycleCreateDTO);

        // Then
        MotorcycleEntity motorcycleEntity = new MotorcycleEntity();
        motorcycleEntity.setCreated(OffsetDateTime.now());
        motorcycleEntity.setStatus(Status.ACTIVE);
        motorcycleEntity.setBrandEntity(motorcycleCreateDTO.getBrand());
        motorcycleEntity.setModel(motorcycleCreateDTO.getModel());
        motorcycleEntity.setType(typeEntity);
        verify(motorcycleRepository).save(motorcycleEntity);
    }

}
