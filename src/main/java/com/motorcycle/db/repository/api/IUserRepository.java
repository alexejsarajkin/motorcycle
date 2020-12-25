package com.motorcycle.db.repository.api;

import com.motorcycle.db.datamodel.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

    // Test - get value from one column
    @Query("select re.id from UserEntity re where re.login = :login")
    Integer retrieveByLogin(@Param("login") String login);

    UserEntity findByLogin(String login);
}
