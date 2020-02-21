package com.motorcycle.service.impl;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.motorcycle.db.datamodel.RoleEntity;
import com.motorcycle.db.datamodel.Status;
import com.motorcycle.db.repository.api.IRoleRepository;
import com.motorcycle.service.api.IRoleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService implements IRoleService {

  private final IRoleRepository roleRepository;

  @Override
  public RoleEntity createRole(RoleEntity roleEntity) {
    roleEntity.setCreated(OffsetDateTime.now());
    roleEntity.setStatus(Status.ACTIVE);
    return roleRepository.save(roleEntity);
  }
}
