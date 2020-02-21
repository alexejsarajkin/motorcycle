package com.motorcycle.endpoint.rest.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
abstract class BaseRestEndpointTest {

  private static final String BASE_URL = "/motorcycles";

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @Before
  public void setUp() {
    this.mockMvc =
        MockMvcBuilders
            .webAppContextSetup(this.wac)
            .build();
  }

  protected static PostgreSQLContainer createPostgresSQLContainer() {
    return new PostgreSQLContainer("postgres")
        .withDatabaseName("postgres")
        .withUsername("admin")
        .withPassword("admin");
  }

  protected String getBaseUrl() {
    return BASE_URL;
  }

  protected MockMvc getMockMvc() {
    return mockMvc;
  }

  private String getFileContent(String filePath) {
    String fileContent = null;
    Path path = Paths.get(filePath);

    try (BufferedReader reader = Files.newBufferedReader(path)) {
      fileContent = reader.lines().collect(Collectors.joining(System.lineSeparator()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return fileContent;
  }

  private String getResourcesIOPath() {
    return String.format("src/test/resources/io/%s/", this.getClass().getSimpleName());
  }

  protected String readRequest(String fileNamePrefix) {
    return getFileContent(getResourcesIOPath() + fileNamePrefix + "-REQUEST.json");
  }

  protected String readResponse(String fileNamePrefix) {
    return getFileContent(getResourcesIOPath() + fileNamePrefix + "-RESPONSE.json");
  }
}
