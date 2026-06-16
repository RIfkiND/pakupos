package com.aulkhami.pakupos.app.exceptions;

public class AuthenticationException extends AppException {

    public AuthenticationException(String message) {
        super(message, "AUTH_ERROR");
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, "AUTH_ERROR", cause);
    }
}
