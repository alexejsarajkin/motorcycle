package com.motorcycle.facade.rest.api;

import com.motorcycle.endpoint.rest.type.*;

import java.util.List;

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
