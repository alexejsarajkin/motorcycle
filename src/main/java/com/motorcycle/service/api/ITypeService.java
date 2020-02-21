package com.motorcycle.service.api;

import java.util.List;

import com.motorcycle.db.datamodel.TypeEntity;

public interface ITypeService {
  List<TypeEntity> retrieveAllType();

  TypeEntity retrieveTypeByName(String name);

  TypeEntity createType(TypeEntity typeEntity);

  void deleteType(Integer id);

  void saveTypeToFile();
}
