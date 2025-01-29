package com.chat.jnd.errorhandling;

import java.util.List;

public class ApiErrorValidation extends ApiError {

    public ApiErrorValidation(List<ErrorField> fields) {
        super(422, "Validation Error", fields);
    }

    public ApiErrorValidation(String message) {
        super(422, message);
    }
}