package com.motorcycle.facade.rest.api;

import java.util.List;

import com.motorcycle.endpoint.rest.type.AuthenticationRequest;
import com.motorcycle.endpoint.rest.type.AuthenticationResponse;
import com.motorcycle.endpoint.rest.type.MotorcycleCreateRequest;
import com.motorcycle.endpoint.rest.type.MotorcycleCreateResponse;
import com.motorcycle.endpoint.rest.type.RoleCreateRequest;
import com.motorcycle.endpoint.rest.type.TypeCreateRequest;
import com.motorcycle.endpoint.rest.type.TypeResponse;
import com.motorcycle.endpoint.rest.type.UserCreateRequest;

public interface IRestFacade {
  List<MotorcycleCreateResponse> retrieveAllMotorcycle();

  MotorcycleCreateResponse retrieveMotorcycleByBrand(String brand);

  MotorcycleCreateResponse createMotorcycle(MotorcycleCreateRequest motorcycleCreateRequest);

  List<TypeResponse> retrieveAllType();

  TypeResponse retrieveTypeByName(String name);

  TypeResponse createType(TypeCreateRequest typeCreateRequest);

  void deleteType(Integer id);

  void saveTypeToFile();

  void createUser(UserCreateRequest userCreateRequest);

  void createRole(RoleCreateRequest roleCreateRequest);

  AuthenticationResponse authentication(AuthenticationRequest authenticationRequest);
}
