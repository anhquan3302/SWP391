package com.example.securityl.exceptions;

public class InsufficientQuantityException extends RuntimeException{
    public InsufficientQuantityException(String message) {
        super(message);
    }
}
