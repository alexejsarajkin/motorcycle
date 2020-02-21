package com.motorcycle.facade.rest.impl;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.motorcycle.db.datamodel.MotorcycleEntity;
import com.motorcycle.db.datamodel.RoleEntity;
import com.motorcycle.db.datamodel.TypeEntity;
import com.motorcycle.db.datamodel.UserEntity;
import com.motorcycle.endpoint.rest.dto.AuthenticationRequestDTO;
import com.motorcycle.endpoint.rest.dto.MotorcycleCreateDTO;
import com.motorcycle.endpoint.rest.dto.UserCreateDTO;
import com.motorcycle.endpoint.rest.type.AuthenticationRequest;
import com.motorcycle.endpoint.rest.type.AuthenticationResponse;
import com.motorcycle.endpoint.rest.type.MotorcycleCreateRequest;
import com.motorcycle.endpoint.rest.type.MotorcycleCreateResponse;
import com.motorcycle.endpoint.rest.type.RoleCreateRequest;
import com.motorcycle.endpoint.rest.type.TypeCreateRequest;
import com.motorcycle.endpoint.rest.type.TypeResponse;
import com.motorcycle.endpoint.rest.type.UserCreateRequest;
import com.motorcycle.exception.NotFoundException;
import com.motorcycle.exception.ValidationException;
import com.motorcycle.facade.mapper.ModelMapper;
import com.motorcycle.facade.rest.api.IRestFacade;
import com.motorcycle.security.TokenProvider;
import com.motorcycle.service.api.IMotorcycleService;
import com.motorcycle.service.api.IRoleService;
import com.motorcycle.service.api.ITypeService;
import com.motorcycle.service.api.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestFacade implements IRestFacade {

  private final ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);

  private final IMotorcycleService motorcycleService;
  private final ITypeService typeService;
  private final IUserService userService;
  private final IRoleService roleService;
  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;

  @Override
  public List<MotorcycleCreateResponse> retrieveAllMotorcycle() {
    List<MotorcycleEntity> motorcycleEntities = motorcycleService.retrieveAllMotorcycle();
    if (motorcycleEntities == null) {
      throw new NotFoundException("Motorcycle list is empty");
    }
    return modelMapper.toMotorcycleCreateResponseList(motorcycleEntities);
  }

  @Override
  public MotorcycleCreateResponse retrieveMotorcycleByBrand(String brand) {
    MotorcycleEntity motorcycleEntity = motorcycleService.retrieveMotorcycleByBrand(brand);
    if (motorcycleEntity == null) {
      throw new NotFoundException("Motorcycle not found (" + brand + ")");
    }
    return modelMapper.toMotorcycleCreateResponse(motorcycleEntity);
  }

  @Override
  public MotorcycleCreateResponse createMotorcycle(MotorcycleCreateRequest motorcycleCreateRequest) {
    List<String> errors = new ArrayList<>();

    if ("ResponseEntityExceptionHandlerBrand".equals(motorcycleCreateRequest.getBrand())) {
      errors.add("Brand is not valid");
    }

    if ("ResponseEntityExceptionHandlerModel".equals(motorcycleCreateRequest.getModel())) {
      errors.add("Model is not valid");
    }

    if ("ResponseEntityExceptionHandlerType".equals(motorcycleCreateRequest.getTypeName())) {
      errors.add("Type is not valid");
    }

    if (!errors.isEmpty()) {
      throw new ValidationException(errors);
    }

    MotorcycleCreateDTO motorcycleCreateDTO = modelMapper.toMotorcycleCreateDTO(motorcycleCreateRequest);
    MotorcycleEntity motorcycleEntity = motorcycleService.createMotorcycle(motorcycleCreateDTO);
    return modelMapper.toMotorcycleCreateResponse(motorcycleEntity);
  }

  @Override
  public List<TypeResponse> retrieveAllType() {
    List<TypeEntity> types = typeService.retrieveAllType();
    if (types == null) {
      throw new NotFoundException("Type list is empty");
    }
    return modelMapper.toTypeResponseList(types);
  }

  @Override
  public TypeResponse retrieveTypeByName(String name) {
    TypeEntity typeEntity = typeService.retrieveTypeByName(name);
    if (typeEntity == null) {
      throw new NotFoundException("Motorcycle not found (" + name + ")");
    }
    return modelMapper.toTypeResponse(typeEntity);
  }

  @Override
  public TypeResponse createType(TypeCreateRequest typeCreateRequest) {
    if (!typeCreateRequest.isValid()) {
      throw new ValidationException(typeCreateRequest.getErrorList());
    }

    TypeEntity typeEntity = modelMapper.toTypeEntity(typeCreateRequest);
    typeService.createType(typeEntity);
    return modelMapper.toTypeResponse(typeEntity);
  }

  @Override
  public void deleteType(Integer id) {
    typeService.deleteType(id);
  }

  @Override
  public void saveTypeToFile() {
    typeService.saveTypeToFile();
  }

  @Override
  public void createUser(UserCreateRequest userCreateRequest) {
    UserCreateDTO userCreateDTO = modelMapper.toUserCreateDTO(userCreateRequest);
    userService.createUser(userCreateDTO);
  }

  @Override
  public void createRole(RoleCreateRequest roleCreateRequest) {
    RoleEntity roleEntity = modelMapper.toRoleEntity(roleCreateRequest);
    roleService.createRole(roleEntity);
  }

  @Override
  public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest) {
    AuthenticationRequestDTO authenticationRequestDTO = modelMapper.toAuthenticationRequestDTO(authenticationRequest);

    try {
      String login = authenticationRequestDTO.getLogin();
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, authenticationRequestDTO.getPassword()));
      UserEntity userEntity = userService.findByLogin(login);

      if (userEntity == null) {
        throw new UsernameNotFoundException("User with login: " + login + " not found");
      }

      String token = tokenProvider.createToken(userEntity);
      return modelMapper.toAuthenticationResponse(token);

    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Invalid username or password");
    }
  }
}
