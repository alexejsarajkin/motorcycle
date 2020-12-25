package com.motorcycle.endpoint.rest.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String login;
    private String password;
}
