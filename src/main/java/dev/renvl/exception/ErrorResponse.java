package dev.renvl.exception;

import java.io.Serializable;
import java.util.List;

public class ErrorResponse implements Serializable {
    private String message;
    private List<String> details;

    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }
}
