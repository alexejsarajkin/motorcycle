package com.motorcycle.endpoint.rest.impl;

import com.motorcycle.endpoint.rest.api.IRestEndpoint;
import com.motorcycle.endpoint.rest.type.*;
import com.motorcycle.endpoint.rest.validator.MotorcycleCreateValidator;
import com.motorcycle.facade.rest.api.IRestFacade;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/motorcycles")
public class RestEndpoint implements IRestEndpoint {

    private final IRestFacade restFacade;

    public RestEndpoint(IRestFacade restFacade) {
        this.restFacade = restFacade;
    }

    @Override
    public List<MotorcycleCreateResponse> motorcycleRetrieveAll() {
        return restFacade.retrieveAllMotorcycle();
    }

    @Override
    public MotorcycleCreateResponse motorcycleRetrieveByBrand(@PathVariable String brand) {
        return restFacade.retrieveMotorcycleByBrand(brand);
    }

    @Override
    public MotorcycleCreateResponse motorcycleCreate(@RequestBody @MotorcycleCreateValidator MotorcycleCreateRequest motorcycleCreateRequest) {
        return restFacade.createMotorcycle(motorcycleCreateRequest);
    }

    @Override
    public List<TypeResponse> typeRetrieveAll() {
        return restFacade.retrieveAllType();
    }

    @Override
    public TypeResponse typeRetrieveByName(@PathVariable String name) {
        return restFacade.retrieveTypeByName(name);
    }

    @Override
    public TypeResponse typeCreate(@RequestBody TypeCreateRequest typeCreateRequest) {
        return restFacade.createType(typeCreateRequest);
    }

    @Override
    public void typeDelete(@PathVariable Integer id) {
        restFacade.deleteType(id);
    }

    @Override
    public void saveTypeToFile() {
        restFacade.saveTypeToFile();
    }

    @Override
    public void userCreate(@RequestBody UserCreateRequest userCreateRequest) {
        restFacade.createUser(userCreateRequest);
    }

    @Override
    public void roleCreate(@RequestBody RoleCreateRequest roleCreateRequest) {
        restFacade.createRole(roleCreateRequest);
    }

    @Override
    public AuthenticationResponse authentication(@RequestBody AuthenticationRequest authenticationRequest) {
        return restFacade.authentication(authenticationRequest);
    }
}
