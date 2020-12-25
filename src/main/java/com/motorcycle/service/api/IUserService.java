package com.motorcycle.service.api;

import com.motorcycle.db.datamodel.UserEntity;
import com.motorcycle.endpoint.rest.dto.UserCreateDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    UserEntity createUser(UserCreateDTO userCreateDTO);

    List<UserEntity> getAllUser();

    UserEntity findByLogin(String login);
}
