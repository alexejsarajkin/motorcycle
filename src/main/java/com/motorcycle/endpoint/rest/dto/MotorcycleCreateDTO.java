package com.motorcycle.endpoint.rest.dto;

import lombok.Data;

@Data
public class MotorcycleCreateDTO {
  private String brand;
  private String model;
  private String typeName;
}
