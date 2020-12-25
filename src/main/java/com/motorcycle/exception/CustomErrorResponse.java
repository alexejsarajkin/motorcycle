package com.motorcycle.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class CustomErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private OffsetDateTime timestamp;
    private String path;
    private HttpStatus statusCode;
    private List<String> errors;
}
