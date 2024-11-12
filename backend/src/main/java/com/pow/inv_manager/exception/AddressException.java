package com.pow.inv_manager.exception;

public class AddressException extends Exception {
  public AddressException(String message) { super(message);
  }

  public AddressException(String message, Throwable cause) { super(message, cause);}
}
