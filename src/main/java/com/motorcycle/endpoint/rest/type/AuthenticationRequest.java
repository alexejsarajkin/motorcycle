package com.motorcycle.endpoint.rest.type;

import lombok.Data;

@Data
public class AuthenticationRequest {
  private String login;
  private String password;
}
