package com.motorcycle.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.motorcycle.db.datamodel.RoleEntity;
import com.motorcycle.db.datamodel.UserEntity;
import com.motorcycle.exception.ValidationException;
import com.motorcycle.property.resolver.PropertyResolver;
import com.motorcycle.service.api.IUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
import lombok.Data;

@Component
@Data
@AllArgsConstructor
public class TokenProvider {

  private final PropertyResolver propertyResolver;
  private final IUserService userService;

  private String getSecretBase64(String secret) {
    return Base64.getEncoder().encodeToString(secret.getBytes());
  }

  public String createToken(UserEntity userEntity) {
    Claims claims = Jwts.claims().setSubject(userEntity.getLogin());
    claims.put("roles", getRoleNames(userEntity.getRoles()));

    Date issuedDateTime = new Date();
    Date expirationDateTime = new Date(issuedDateTime.getTime() + propertyResolver.getTokenExpired());

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(issuedDateTime)
        .setExpiration(expirationDateTime)
        .signWith(SignatureAlgorithm.HS256, getSecretBase64(propertyResolver.getTokenSecret()))
        .compact();
  }

  public Authentication getAuthentication(String token) {
    String login = getUsername(token);
    UserDetails userDetails = this.userService.loadUserByUsername(login);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(getSecretBase64(propertyResolver.getTokenSecret())).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(getSecretBase64(propertyResolver.getTokenSecret())).parseClaimsJws(token);

      return !claims.getBody().getExpiration().before(new Date());
    } catch (SignatureException ex) {
      throw new ValidationException("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      throw new ValidationException("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      throw new ValidationException("Expired JWT token");
    }
  }

  private List<String> getRoleNames(List<RoleEntity> roleEntities) {
    return roleEntities.stream()
        .map(RoleEntity::getName).collect(Collectors.toList());
  }
}
