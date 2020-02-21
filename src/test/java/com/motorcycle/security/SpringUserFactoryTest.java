package com.motorcycle.security;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.motorcycle.db.datamodel.RoleEntity;
import com.motorcycle.db.datamodel.Status;
import com.motorcycle.db.datamodel.UserEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class SpringUserFactoryTest {

  private RoleEntity createRoleEntity(String name) {
    RoleEntity roleEntity = new RoleEntity();
    roleEntity.setName(name);
    roleEntity.setCreated(OffsetDateTime.now());
    roleEntity.setStatus(Status.ACTIVE);
    return roleEntity;
  }

  private UserEntity createUserEntity() {
    UserEntity userEntity = new UserEntity();
    userEntity.setLogin("admin");
    userEntity.setFirstName("admin first name");
    userEntity.setLastName("admin last name");
    userEntity.setPassword("password");
    userEntity.setRoles(Collections.singletonList(createRoleEntity("ROLE_ADMIN")));
    userEntity.setCreated(OffsetDateTime.now());
    userEntity.setStatus(Status.ACTIVE);
    return userEntity;
  }

  @Test
  public void createSpringUser() {
    // Given
    UserEntity userEntity = createUserEntity();

    // When
    SpringUser springUser = SpringUserFactory.createSpringUser(userEntity);

    // Then
    assertNotNull("Spring user is null", springUser);
    assertTrue("Spring user status is not active", springUser.isEnabled());
    assertTrue("Spring user status is expired", springUser.isAccountNonExpired());
    assertTrue("Spring user status is locked", springUser.isAccountNonLocked());
    assertTrue("Spring user credentials are expired", springUser.isCredentialsNonExpired());
    assertEquals("Spring user name does not equals login", userEntity.getLogin(), springUser.getUsername());
    assertEquals("Spring user password does not equals user password", userEntity.getPassword(), springUser.getPassword());
    assertThat("Spring user authority does not contains user role",
        userEntity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()),
        is(springUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())));
  }
}
