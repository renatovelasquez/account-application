package dev.renvl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String exception) {
        super(exception);
    }
}