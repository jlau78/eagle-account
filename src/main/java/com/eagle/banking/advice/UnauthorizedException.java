package com.eagle.banking.advice;

public class UnauthorizedException extends Exception {

  public UnauthorizedException(String message) {
    super(message);
  }

  public UnauthorizedException(String message, Throwable e) {
    super(message, e);
  }
}
