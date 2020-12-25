package com.motorcycle.facade.mvc.impl;

import com.motorcycle.db.datamodel.TypeEntity;
import com.motorcycle.endpoint.rest.type.TypeResponse;
import com.motorcycle.facade.mapper.ModelMapper;
import com.motorcycle.facade.mvc.api.IMvcFacade;
import com.motorcycle.service.api.ITypeService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MvcFacade implements IMvcFacade {

    private final ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);

    private final ITypeService typeService;

    @Override
    public ModelAndView retrieveAllType() {
        List<TypeEntity> types = typeService.retrieveAllType();
        List<TypeResponse> typeResponses = modelMapper.toTypeResponseList(types);

        ModelAndView modelAndView = createModelAndView("type-all");
        modelAndView.addObject("list", typeResponses);

        return modelAndView;
    }

    private ModelAndView createModelAndView(String viewName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
