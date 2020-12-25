package com.motorcycle.endpoint.mvc.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public interface IMvcEndpoint {

    @GetMapping("/type/all")
    ModelAndView typeRetrieveAll();
}
