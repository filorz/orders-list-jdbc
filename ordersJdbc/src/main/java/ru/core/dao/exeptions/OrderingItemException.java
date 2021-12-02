package ru.core.dao.exeptions;

public class OrderingItemException extends RuntimeException {
    public OrderingItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
