package com.motorcycle.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.motorcycle.db.datamodel.RoleEntity;
import com.motorcycle.db.datamodel.Status;
import com.motorcycle.db.datamodel.UserEntity;

public class SpringUserFactory {

  public static SpringUser createSpringUser(UserEntity userEntity) {
    SpringUser springUser = new SpringUser();
    springUser.setUsername(userEntity.getLogin());
    springUser.setPassword(userEntity.getPassword());
    springUser.setAuthorities(toGrantedAuthorities(userEntity.getRoles()));
    springUser.setEnabled(userEntity.getStatus().equals(Status.ACTIVE));
    return springUser;
  }

  private static List<GrantedAuthority> toGrantedAuthorities(List<RoleEntity> roleEntities) {
    return roleEntities.stream()
        .map(e -> new SimpleGrantedAuthority(e.getName()))
        .collect(Collectors.toList());
  }
}
