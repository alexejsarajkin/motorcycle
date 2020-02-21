package com.motorcycle.exception;

import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class ValidationException extends RuntimeException {

  private List<String> errors;

  public ValidationException(List<String> errors) {
    super(errors.toString());
    this.errors = errors;
  }

  public ValidationException(String error) {
    super(error);
    this.errors = Collections.singletonList(error);
  }
}
