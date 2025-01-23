package com.district12.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestRuntimeException extends RuntimeException {
    public BadRequestRuntimeException(String message) {
        super(message);
    }
}
