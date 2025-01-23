package com.district12.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
public class TimedOutException extends RuntimeException{
    public TimedOutException(String message) {
        super(message);
    }
}
