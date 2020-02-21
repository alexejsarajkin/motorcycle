package com.motorcycle.endpoint.rest.type;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.motorcycle.endpoint.rest.validator.MotorcycleCreateValidator;

import lombok.Data;

@Data
public class MotorcycleCreateRequest implements ConstraintValidator<MotorcycleCreateValidator, MotorcycleCreateRequest> {
  private String brand;
  private String model;
  private String typeName;

  @Override
  public boolean isValid(MotorcycleCreateRequest motorcycleCreateRequest, ConstraintValidatorContext constraintValidatorContext) {

    if (motorcycleCreateRequest == null) {
      return true;
    }

    boolean isValid = true;

    if ("ConstraintValidatorBrand".equals(motorcycleCreateRequest.getBrand())) {
      addMessage(constraintValidatorContext, "{MotorcycleCreateRequest.brand.message}");
      isValid = false;
    }

    if ("ConstraintValidatorModel".equals(motorcycleCreateRequest.getModel())) {
      addMessage(constraintValidatorContext, "{MotorcycleCreateRequest.model.message}");
      isValid = false;
    }

    if ("ConstraintValidatorType".equals(motorcycleCreateRequest.getTypeName())) {
      addMessage(constraintValidatorContext, "{MotorcycleCreateRequest.type.message}");
      isValid = false;
    }

    return isValid;
  }

  private void addMessage(ConstraintValidatorContext context, String template) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(template).addConstraintViolation();
  }
}