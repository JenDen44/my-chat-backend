package com.chat.jnd.errorhandling;

public class ApiErrorNotFound extends ApiError {

    public ApiErrorNotFound(String message) {
        super(404, message);
    }
}