package com.motorcycle.endpoint.rest.api;

import com.motorcycle.endpoint.rest.type.*;
import com.motorcycle.endpoint.rest.validator.MotorcycleCreateValidator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IRestEndpoint {
    @GetMapping(value = "/motorcycle/all", produces = "application/json")
    @ResponseBody
    List<MotorcycleCreateResponse> motorcycleRetrieveAll();

    @GetMapping(value = "/motorcycle/{brand}", produces = "application/json")
    @ResponseBody
    MotorcycleCreateResponse motorcycleRetrieveByBrand(@PathVariable(name = "brand") String brand);

    @PostMapping(value = "/motorcycle/create", consumes = "application/json", produces = "application/json")
    @ResponseBody
    MotorcycleCreateResponse motorcycleCreate(@RequestBody @MotorcycleCreateValidator MotorcycleCreateRequest motorcycleCreateRequest);

    @GetMapping(value = "/type/all", produces = "application/json")
    @ResponseBody
    List<TypeResponse> typeRetrieveAll();

    @GetMapping(value = "/type/{name}", produces = "application/json")
    @ResponseBody
    TypeResponse typeRetrieveByName(@PathVariable(name = "name") String name);

    @PostMapping(value = "/type/create", consumes = "application/json", produces = "application/json")
    @ResponseBody
    TypeResponse typeCreate(@RequestBody TypeCreateRequest typeCreateRequest);

    @DeleteMapping(value = "/type/delete/{id}")
    void typeDelete(@PathVariable(name = "id") Integer id);

    @PostMapping(value = "/type/savetofile", produces = "application/json")
    @ResponseBody
    void saveTypeToFile();

    @PostMapping(value = "/user/create", consumes = "application/json", produces = "application/json")
    @ResponseBody
    void userCreate(@RequestBody UserCreateRequest userCreateRequest);

    @PostMapping(value = "/role/create", consumes = "application/json", produces = "application/json")
    @ResponseBody
    void roleCreate(@RequestBody RoleCreateRequest roleCreateRequest);

    @PostMapping(value = "/authentication", consumes = "application/json", produces = "application/json")
    @ResponseBody
    AuthenticationResponse authentication(@RequestBody AuthenticationRequest authenticationRequest);
}
