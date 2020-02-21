package com.motorcycle.db.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motorcycle.db.datamodel.RoleEntity;

public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {

  RoleEntity findByName(String name);
}
