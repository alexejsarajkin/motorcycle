package com.motorcycle.exception;

import java.time.OffsetDateTime;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<CustomErrorResponse> customHandleNotFound(NotFoundException ex, WebRequest request) {
    CustomErrorResponse customErrorResponse = new CustomErrorResponse();
    customErrorResponse.setPath(getPath(request));
    customErrorResponse.setStatusCode(HttpStatus.NOT_FOUND);
    customErrorResponse.setTimestamp(OffsetDateTime.now());
    customErrorResponse.setErrors(Collections.singletonList(ex.getMessage()));
    return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ValidationException.class)
  protected ResponseEntity<Object> customHandleValidationException(ValidationException ex, WebRequest request) {
    CustomErrorResponse customErrorResponse = new CustomErrorResponse();
    customErrorResponse.setPath(getPath(request));
    customErrorResponse.setStatusCode(HttpStatus.BAD_REQUEST);
    customErrorResponse.setTimestamp(OffsetDateTime.now());
    customErrorResponse.setErrors(ex.getErrors());
    return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
  }

  private String getPath(WebRequest webRequest) {
    return ((ServletWebRequest) webRequest).getRequest().getRequestURI();
  }
}
