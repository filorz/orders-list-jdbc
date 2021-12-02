package ru.core.dao.exeptions;

public class OrderingException extends RuntimeException {
    public OrderingException(String message, Throwable cause) {
        super(message, cause);
    }
}
