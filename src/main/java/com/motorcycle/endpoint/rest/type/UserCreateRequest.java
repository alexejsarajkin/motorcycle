package com.motorcycle.endpoint.rest.type;

import java.util.List;

import lombok.Data;

@Data
public class UserCreateRequest {
  private String login;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private List<String> roles;
}
