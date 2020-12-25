package com.motorcycle.endpoint.config;

import com.motorcycle.endpoint.mvc.impl.MvcEndpoint;
import com.motorcycle.endpoint.rest.impl.RestEndpoint;
import com.motorcycle.facade.mvc.api.IMvcFacade;
import com.motorcycle.facade.rest.api.IRestFacade;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class EndpointConfig {

    @Bean
    public MvcEndpoint mvcEndpoint(IMvcFacade mvcFacade) {
        return new MvcEndpoint(mvcFacade);
    }

    @Bean
    public RestEndpoint restEndpoint(IRestFacade restFacade) {
        return new RestEndpoint(restFacade);
    }
}
