package com.motorcycle.endpoint.rest.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.motorcycle.endpoint.rest.type.AuthenticationRequest;
import com.motorcycle.endpoint.rest.type.AuthenticationResponse;
import com.motorcycle.endpoint.rest.type.MotorcycleCreateRequest;
import com.motorcycle.endpoint.rest.type.MotorcycleCreateResponse;
import com.motorcycle.endpoint.rest.type.RoleCreateRequest;
import com.motorcycle.endpoint.rest.type.TypeCreateRequest;
import com.motorcycle.endpoint.rest.type.TypeResponse;
import com.motorcycle.endpoint.rest.type.UserCreateRequest;
import com.motorcycle.endpoint.rest.validator.MotorcycleCreateValidator;

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
