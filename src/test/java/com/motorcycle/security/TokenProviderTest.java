package com.motorcycle.security;

import com.motorcycle.db.datamodel.RoleEntity;
import com.motorcycle.db.datamodel.Status;
import com.motorcycle.db.datamodel.UserEntity;
import com.motorcycle.exception.ValidationException;
import com.motorcycle.property.resolver.PropertyResolver;
import com.motorcycle.service.api.IUserService;
import org.assertj.core.util.Strings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
public class TokenProviderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private PropertyResolver propertyResolver;

    @Mock
    private IUserService userService;

    @InjectMocks
    private TokenProvider tokenProvider;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Before
    public void setUp() {
        tokenProvider = new TokenProvider(propertyResolver, userService);
    }

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
    public void createToken() {
        // Given
        UserEntity userEntity = createUserEntity();
        doReturn("ride_or_die_free_ride_born_to_ride").when(propertyResolver).getTokenSecret();
        doReturn(360000L).when(propertyResolver).getTokenExpired();

        // When
        String token = tokenProvider.createToken(userEntity);

        // Then
        assertFalse("Token is empty", Strings.isNullOrEmpty(token));
        assertTrue("Toke is invalid", tokenProvider.validateToken(token));
    }

    public SpringUser createSpringUser() {
        SpringUser springUser = new SpringUser();
        springUser.setUsername("admin");
        springUser.setPassword("$2a$10$Wz3rxHRXYV6hwfRpYbMns.yyTlP2r8p1oykhpWYIF7zLEKa49xNyS");
        springUser.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        springUser.setEnabled(true);
        return springUser;
    }

    @Test
    public void getAuthentication() {
        // Given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3OTkwMzY4LCJleHAiOjI1Nzc5OTA3Mjh9.mbVgNckXY9jhextFrqIkuaG7AofxrVv5Kvx2pKnkkpI";
        doReturn("ride_or_die_free_ride_born_to_ride").when(propertyResolver).getTokenSecret();

        UserDetails userDetails = createSpringUser();
        doReturn(userDetails).when(userService).loadUserByUsername("admin");

        // When
        Authentication authentication = tokenProvider.getAuthentication(token);

        // Then
        assertNotNull("Authentication is null", authentication);
        assertTrue("User is not authenticated", authentication.isAuthenticated());

    }

    @Test
    public void getUsername() {
        // Given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3OTkwMzY4LCJleHAiOjI1Nzc5OTA3Mjh9.mbVgNckXY9jhextFrqIkuaG7AofxrVv5Kvx2pKnkkpI";
        doReturn("ride_or_die_free_ride_born_to_ride").when(propertyResolver).getTokenSecret();

        // When
        String userName = tokenProvider.getUsername(token);

        // Then
        assertFalse("User name is empty", Strings.isNullOrEmpty(userName));
    }

    @Test
    public void resolveToken() {
        // Given
        String bearerToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3OTkwMzY4LCJleHAiOjE1Nzc5OTA3Mjh9.R2bG6Y2-ANGicEnbxPEW2ptEzQTPH7XjUHGdO2B0XVo";
        doReturn(bearerToken).when(httpServletRequest).getHeader("Authorization");

        // When
        String token = tokenProvider.resolveToken(httpServletRequest);

        // Then
        assertFalse("Token is empty", Strings.isNullOrEmpty(token));
        assertEquals("Wrong token", bearerToken.substring(7), token);
    }

    @Test
    public void resolveToken_TokenIsNull() {
        // Given
        doReturn(null).when(httpServletRequest).getHeader("Authorization");

        // When
        String token = tokenProvider.resolveToken(httpServletRequest);

        // Then
        assertNull("Token is not null", token);
    }

    @Test
    public void resolveToken_TokenStartWithNotBearer() {
        // Given
        String bearerToken = "Basic eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3OTkwMzY4LCJleHAiOjE1Nzc5OTA3Mjh9.R2bG6Y2-ANGicEnbxPEW2ptEzQTPH7XjUHGdO2B0XVo";
        doReturn(bearerToken).when(httpServletRequest).getHeader("Authorization");

        // When
        String token = tokenProvider.resolveToken(httpServletRequest);

        // Then
        assertNull("Token start with not Bearer", token);
    }

    @Test
    public void validateToken_InvalidJWTSignature() throws ValidationException {
        // Given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3OTc0MDkwLCJleHAiOjE1Nzc5NzQwOTN9._PY4YpEUOg7_RS8g6PvuxGkoJ4r8EcxE0y59lbVAY0w";
        doReturn("rideordieWrong").when(propertyResolver).getTokenSecret();

        // Then
        exception.expect(ValidationException.class);
        exception.expectMessage(containsString("Invalid JWT signature"));

        // When
        Boolean isValid = tokenProvider.validateToken(token);

        // Then
        Assert.assertFalse(isValid);
    }

    @Test
    public void validateToken_InvalidJWTToken() throws ValidationException {
        // Given
        String token = "eyJhbGciOiJIUzI1Ni.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3OTc0MDkwLCJleHAiOjE1Nzc5NzQwOTN9._PY4YpEUOg7_RS8g6PvuxGkoJ4r8EcxE0y59lbVAY0w";
        doReturn("rideordie").when(propertyResolver).getTokenSecret();

        // Then
        exception.expect(ValidationException.class);
        exception.expectMessage(containsString("Invalid JWT token"));

        // When
        Boolean isValid = tokenProvider.validateToken(token);

        // Then
        Assert.assertFalse(isValid);
    }

    @Test
    public void validateToken_ExpiredJwtToken() throws ValidationException {
        // Given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3OTc0MDkwLCJleHAiOjE1Nzc5NzQwOTN9._PY4YpEUOg7_RS8g6PvuxGkoJ4r8EcxE0y59lbVAY0w";
        doReturn("rideordie").when(propertyResolver).getTokenSecret();

        // Then
        exception.expect(ValidationException.class);
        exception.expectMessage(containsString("Expired JWT token"));

        // When
        Boolean isValid = tokenProvider.validateToken(token);

        // Then
        Assert.assertFalse(isValid);
    }

    @Test
    public void validateToken_JWTClaimsStringIsEmpty() {
        // Given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc3OTgzOTc2LCJleHAiOjI1Nzc5ODM5ODB9.QztnoIhW71z2GT9NX4L6MBZd2eZ5FL9YWQx7hXCoLIE";
        doReturn("ride_or_die_free_ride_born_to_ride").when(propertyResolver).getTokenSecret();

        // When
        Boolean isValid = tokenProvider.validateToken(token);

        // Then
        assertTrue("Token is not valid", isValid);
    }
}
