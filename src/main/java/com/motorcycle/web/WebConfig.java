package com.motorcycle.web;

import com.motorcycle.endpoint.config.EndpointConfig;
import com.motorcycle.service.config.ServiceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

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
