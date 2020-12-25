package com.motorcycle.property.resolver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertyResolver {

    @Value("${jwt.token.token_secret}")
    private String tokenSecret;

    @Value("${jwt.token.token_expired}")
    private long tokenExpired;

    public String getTokenSecret() {
        return tokenSecret;
    }

    public long getTokenExpired() {
        return tokenExpired;
    }
}
