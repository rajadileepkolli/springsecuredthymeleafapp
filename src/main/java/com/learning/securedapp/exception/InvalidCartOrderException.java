package com.learning.securedapp.exception;

public class InvalidCartOrderException extends RuntimeException {
    private static final long serialVersionUID = -7991559713697297612L;

    public InvalidCartOrderException(String message) {
        super(message);
    }

}
