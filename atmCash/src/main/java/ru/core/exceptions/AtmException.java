package ru.core.exceptions;

public class AtmException extends RuntimeException {
    public AtmException(String message, Throwable cause) {
        super(message, cause);
    }
}
