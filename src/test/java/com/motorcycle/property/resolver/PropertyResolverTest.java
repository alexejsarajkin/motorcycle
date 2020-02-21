package com.motorcycle.property.resolver;

import org.assertj.core.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PropertyResolverTest {

  @Autowired
  private PropertyResolver propertyResolver;

  @Test
  public void getTokenSecret() {
    // Given
    String tokenSecret = propertyResolver.getTokenSecret();

    // Then
    assertThat("Property tokenSecret does not exist", propertyResolver, hasProperty("tokenSecret"));
    assertFalse("Property tokenSecret is empty", Strings.isNullOrEmpty(tokenSecret));
  }

  @Test
  public void getTokenExpired() {
    // Given
    Long tokenExpired = propertyResolver.getTokenExpired();

    // Then
    assertThat("Property tokenExpired does not exist", propertyResolver, hasProperty("tokenExpired"));
    assertTrue("Property tokenExpired must by > 0", tokenExpired > 0);
  }
}