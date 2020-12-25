package com.motorcycle.endpoint.rest.type;

import lombok.Data;

@Data
public class MotorcycleCreateResponse {
    private Integer id;
    private String brandResponse;
    private String model;
    private TypeResponse type;
}
