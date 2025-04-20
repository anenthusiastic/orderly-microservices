package com.fatih.userservice.exception;

public class InvalidUserInputException extends RuntimeException {

    public InvalidUserInputException(String message) {
        super(message);
    }

    public InvalidUserInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
