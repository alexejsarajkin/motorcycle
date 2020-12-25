package com.motorcycle.endpoint.rest.impl;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;
import org.testcontainers.containers.PostgreSQLContainer;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ContextConfiguration(initializers = {PostCreateMotorcycleTest.Initializer.class})
public class PostCreateMotorcycleTest extends BaseRestEndpointTest {

    private static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTc4MDA4OTM0LCJleHAiOjM1NzgwMDkyOTR9.V9_Z3kuoR88I-2JaEkXsL8FmDlMgUjFS4_EUv3qLs2A";

    @Rule
    public ExpectedException exception = ExpectedException.none();

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
    public void createMotorcycle() throws Exception {
        // Given
        String fileNamePrefix = "create-motorcycle";

        // When
        MvcResult mvcResult = getMockMvc().perform(
                post(getBaseUrl() + "/motorcycle/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + JWT_TOKEN)
                        .content(readRequest(fileNamePrefix)))
                .andDo(print())
                .andReturn();

        // Then
        assertEquals("Wrong response status", HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertJsonEquals(readResponse(fileNamePrefix), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void createMotorcycle_ResponseEntityExceptionHandler() throws Exception {
        // Given
        String fileNamePrefix = "response-entity-exception-handler";

        // When
        MvcResult mvcResult = getMockMvc().perform(
                post(getBaseUrl() + "/motorcycle/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + JWT_TOKEN)
                        .content(readRequest(fileNamePrefix)))
                .andDo(print())
                .andReturn();

        // Then
        assertEquals("Wrong response status", HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertJsonEquals(readResponse(fileNamePrefix), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void createMotorcycle_ConstraintValidation() throws Exception {
        // Given
        String fileNamePrefix = "constraint-validation";

        // Then
        exception.expect(NestedServletException.class);
        exception.expectMessage(containsString("Brand is not valid"));
        exception.expectMessage(containsString("Model is not valid"));
        exception.expectMessage(containsString("Type is not valid"));

        // When
        MvcResult mvcResult = getMockMvc().perform(
                post(getBaseUrl() + "/motorcycle/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + JWT_TOKEN)
                        .content(readRequest(fileNamePrefix)))
                .andReturn();

        // Then
        assertNotNull("Result is not null", mvcResult);
    }
}
