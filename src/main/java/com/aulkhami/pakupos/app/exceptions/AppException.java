package com.aulkhami.pakupos.app.exceptions;

public class AppException extends RuntimeException {

    private final String errorCode;

    public AppException(String message) {
        this(message, null, null);
    }

    public AppException(String message, Throwable cause) {
        this(message, null, cause);
    }

    public AppException(String message, String errorCode) {
        this(message, errorCode, null);
    }

    public AppException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
