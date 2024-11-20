package com.dlphsolutions.demo_crud.infrastructure.exception;

public class ItemServiceException extends RuntimeException {
    public ItemServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}