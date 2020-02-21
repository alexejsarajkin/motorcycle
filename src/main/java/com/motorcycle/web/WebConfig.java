package com.motorcycle.web;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.motorcycle.endpoint.config.EndpointConfig;
import com.motorcycle.service.config.ServiceConfig;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Import({
    EndpointConfig.class,
    ServiceConfig.class})
@Slf4j
public class WebConfig {

  @PostConstruct
  public void init() {
    log.info("Starting application...");
  }
}
