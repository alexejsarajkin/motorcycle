package com.motorcycle.endpoint.rest.impl;

import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ContextConfiguration(initializers = {PostAuthenticationTest.Initializer.class})
public class PostAuthenticationTest extends BaseRestEndpointTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = createPostgresSQLContainer();

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void authentication() throws Exception {
        // Given
        String fileNamePrefix = "authentication";

        // When
        MvcResult mvcResult = getMockMvc().perform(
                post(getBaseUrl() + "/authentication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readRequest(fileNamePrefix)))
                .andDo(print())
                .andReturn();

        // Then
        assertEquals("Wrong response status", HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertJsonEquals(readResponse(fileNamePrefix), mvcResult.getResponse().getContentAsString());
    }
}
