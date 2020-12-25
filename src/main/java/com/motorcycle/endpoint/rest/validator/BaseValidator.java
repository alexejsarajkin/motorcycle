package com.motorcycle.endpoint.rest.validator;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseValidator {
    private List<String> errorList = new ArrayList<>();

    public Boolean isValid() {
        validate();
        return errorList.isEmpty();
    }

    public void addError(String message) {
        errorList.add(message);
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public abstract void validate();
}
