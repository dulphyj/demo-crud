package com.dlphsolutions.demo_crud.infrastructure.exception;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
