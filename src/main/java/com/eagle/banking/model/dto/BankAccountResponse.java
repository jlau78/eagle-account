package com.eagle.banking.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BankAccountResponse implements Response {

  private String accountNumber;

  private String sortCode;

  private String name;

  private String accountType;

  private String balance;

  private String currency;

  private String createdTimestamp;

  private String updatedTimestamp;
}
