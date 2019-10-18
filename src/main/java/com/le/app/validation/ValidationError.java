package com.le.app.validation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errorMessageList = new ArrayList<>();
    private final String errorMessage;

    public ValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void addValidationErrorMessage(String error) {
        errorMessageList.add(error);
    }

    public List<String> getErrors() {
        return errorMessageList;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
