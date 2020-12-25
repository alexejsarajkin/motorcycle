package com.motorcycle.service.impl;

import com.motorcycle.db.datamodel.Status;
import com.motorcycle.db.datamodel.TypeEntity;
import com.motorcycle.db.repository.api.ITypeRepository;
import com.motorcycle.service.api.ITypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TypeService implements ITypeService {

    private final ITypeRepository typeRepository;

    @Override
    public List<TypeEntity> retrieveAllType() {
        return typeRepository.findAll();
    }

    @Override
    public TypeEntity retrieveTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public TypeEntity createType(TypeEntity typeEntity) {
        typeEntity.setCreated(OffsetDateTime.now());
        typeEntity.setStatus(Status.ACTIVE);
        return typeRepository.save(typeEntity);
    }

    @Override
    public void deleteType(Integer id) {
        typeRepository.deleteById(id);
    }

    @Override
    public void saveTypeToFile() {
        Path path = Paths.get("src/main/resources/import.sql");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (TypeEntity type : typeRepository.findAll()) {
                writer.write("INSERT INTO public.type (name) VALUES ('" + type.getName() + "');\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
