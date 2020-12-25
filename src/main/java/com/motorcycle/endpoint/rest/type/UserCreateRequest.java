package com.motorcycle.endpoint.rest.type;

import lombok.Data;

import java.util.List;

@Data
public class UserCreateRequest {
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> roles;
}
