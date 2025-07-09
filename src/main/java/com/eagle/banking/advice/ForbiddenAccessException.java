package com.eagle.banking.advice;

public class ForbiddenAccessException extends Exception {

  public ForbiddenAccessException(String message) {
    super(message);
  }

  public ForbiddenAccessException(String message, Throwable e) {
    super(message, e);
  }
}
