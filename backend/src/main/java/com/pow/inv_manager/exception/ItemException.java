package com.pow.inv_manager.exception;

public class ItemException extends Exception {
    public ItemException(String message) { super(message);
    }

    public ItemException(String message, Throwable cause) { super(message, cause);}
}
