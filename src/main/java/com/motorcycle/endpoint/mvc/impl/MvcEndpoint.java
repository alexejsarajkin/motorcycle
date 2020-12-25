package com.motorcycle.endpoint.mvc.impl;

import com.motorcycle.endpoint.mvc.api.IMvcEndpoint;
import com.motorcycle.facade.mvc.api.IMvcFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/mvc/motorcycles")
public class MvcEndpoint implements IMvcEndpoint {

    private final IMvcFacade mvcFacade;

    public MvcEndpoint(IMvcFacade mvcFacade) {
        this.mvcFacade = mvcFacade;
    }

    @Override
    public ModelAndView typeRetrieveAll() {
        return mvcFacade.retrieveAllType();
    }
}
