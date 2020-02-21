package com.motorcycle.exception;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CustomErrorResponse {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
  private OffsetDateTime timestamp;
  private String path;
  private HttpStatus statusCode;
  private List<String> errors;
}
