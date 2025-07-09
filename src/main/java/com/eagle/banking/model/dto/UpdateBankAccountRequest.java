package com.eagle.banking.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

@Builder
@Data
public class UpdateBankAccountRequest {

  private String name;

  private AccountType accountType;

}
