package com.eagle.banking.model.dto;

import java.util.Arrays;

public enum AccountType {
  PERSONAL("peronal");

  String value;

  AccountType(String value) {
    this.value = value;
  }

  public static AccountType valueOfEnum(String value) {
    return Arrays.stream(values())
        .filter(v -> v.equals(value))
        .findFirst()
        .get();
  }
}
