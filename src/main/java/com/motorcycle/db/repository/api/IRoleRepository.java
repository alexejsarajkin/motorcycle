package com.motorcycle.db.repository.api;

import com.motorcycle.db.datamodel.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByName(String name);
}
