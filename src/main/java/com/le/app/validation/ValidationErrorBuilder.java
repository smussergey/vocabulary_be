package com.le.app.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class ValidationErrorBuilder {
    public static ValidationError fromBindingErrors(Errors errors) {
        ValidationError validationError = new ValidationError("Validation failed. "
                + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            validationError.addValidationErrorMessage(objectError.getDefaultMessage());
        }
        return validationError;
    }
}

