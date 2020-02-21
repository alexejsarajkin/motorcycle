package com.motorcycle.service.impl;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.motorcycle.db.datamodel.RoleEntity;
import com.motorcycle.db.datamodel.Status;
import com.motorcycle.db.datamodel.UserEntity;
import com.motorcycle.db.repository.api.IRoleRepository;
import com.motorcycle.db.repository.api.IUserRepository;
import com.motorcycle.endpoint.rest.dto.UserCreateDTO;
import com.motorcycle.exception.NotFoundException;
import com.motorcycle.security.SpringUserFactory;
import com.motorcycle.service.api.IUserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

  private final IUserRepository userRepository;
  private final IRoleRepository roleRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public UserEntity createUser(UserCreateDTO userCreateDTO) {
    UserEntity userEntity = new UserEntity();
    userEntity.setCreated(OffsetDateTime.now());
    userEntity.setStatus(Status.ACTIVE);
    userEntity.setLogin(userCreateDTO.getLogin());
    userEntity.setFirstName(userCreateDTO.getFirstName());
    userEntity.setLastName(userCreateDTO.getLastName());
    userEntity.setEmail(userCreateDTO.getEmail());
    userEntity.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
    userEntity.setRoles(getRoles(userCreateDTO.getRoles()));
    return userRepository.save(userEntity);
  }

  private List<RoleEntity> getRoles(List<String> roles) {
    if (roles == null) {
      throw new NotFoundException("Role list is empty");
    }
    return roles.stream().map(roleRepository::findByName).collect(Collectors.toList());
  }

  @Override
  public List<UserEntity> getAllUser() {
    return userRepository.findAll();
  }

  @Override
  public UserEntity findByLogin(String login) {
    return userRepository.findByLogin(login);
  }

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByLogin(login);

    if (userEntity == null) {
      throw new UsernameNotFoundException("Login not found - " + login);
    }

    return SpringUserFactory.createSpringUser(userEntity);
  }
}
