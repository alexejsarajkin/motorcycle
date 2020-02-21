package com.motorcycle.facade.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.motorcycle.db.datamodel.MotorcycleEntity;
import com.motorcycle.db.datamodel.RoleEntity;
import com.motorcycle.db.datamodel.TypeEntity;
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

@Mapper
public abstract class ModelMapper {

  public abstract MotorcycleCreateDTO toMotorcycleCreateDTO(MotorcycleCreateRequest motorcycleCreateRequest);

  @Mapping(source = "brandEntity", target = "brandResponse")
  public abstract MotorcycleCreateResponse toMotorcycleCreateResponse(MotorcycleEntity motorcycleEntity);

  public abstract List<MotorcycleCreateResponse> toMotorcycleCreateResponseList(List<MotorcycleEntity> motorcycleEntityList);

  public abstract TypeEntity toTypeEntity(TypeCreateRequest typeCreateRequest);

  public abstract TypeResponse toTypeResponse(TypeEntity typeEntity);

  public abstract List<TypeResponse> toTypeResponseList(List<TypeEntity> typeEntityList);

  public abstract UserCreateDTO toUserCreateDTO(UserCreateRequest userCreateRequest);

  public abstract RoleEntity toRoleEntity(RoleCreateRequest roleCreateRequest);

  public abstract AuthenticationRequestDTO toAuthenticationRequestDTO(AuthenticationRequest authenticationRequest);

  public abstract AuthenticationResponse toAuthenticationResponse(String token);
}
