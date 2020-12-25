package com.motorcycle.service.api;

import com.motorcycle.db.datamodel.TypeEntity;

import java.util.List;

public interface ITypeService {
    List<TypeEntity> retrieveAllType();

    TypeEntity retrieveTypeByName(String name);

    TypeEntity createType(TypeEntity typeEntity);

    void deleteType(Integer id);

    void saveTypeToFile();
}
