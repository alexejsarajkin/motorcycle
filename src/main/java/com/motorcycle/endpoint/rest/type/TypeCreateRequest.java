package com.motorcycle.endpoint.rest.type;

import com.motorcycle.endpoint.rest.validator.BaseValidator;

import lombok.Data;

@Data
public class TypeCreateRequest extends BaseValidator {
  private String name;

  @Override
  public void validate() {
    if ("TestValidator".equalsIgnoreCase(getName()) ) {
      addError("Test validator error 1");
    }

    if ("TestValidator".equalsIgnoreCase(getName()) ) {
      addError("Test validator error 2");
    }

    if ("TestValidator".equalsIgnoreCase(getName()) ) {
      addError("Test validator error 3");
    }

    if ("TestValidator".equalsIgnoreCase(getName()) ) {
      addError("Test validator error 4");
    }
  }
}
