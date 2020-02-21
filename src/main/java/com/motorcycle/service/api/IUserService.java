package com.motorcycle.service.api;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.motorcycle.db.datamodel.UserEntity;
import com.motorcycle.endpoint.rest.dto.UserCreateDTO;

public interface IUserService extends UserDetailsService {
  UserEntity createUser(UserCreateDTO userCreateDTO);

  List<UserEntity> getAllUser();

  UserEntity findByLogin(String login);
}
