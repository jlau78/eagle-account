package com.eagle.banking.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

@Builder
@Data
public class CreateBankAccountRequest {

  @NonNull
  private String name;

  @NonNull
  private AccountType accountType;

}
