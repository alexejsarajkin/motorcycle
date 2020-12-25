package com.motorcycle.endpoint.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserCreateDTO {
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> roles;
}
