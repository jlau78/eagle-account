package com.eagle.banking.model.entity;

import com.eagle.banking.model.dto.AccountType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String accountNumber;

  private String sortCode;

  private String name;

  private AccountType accountType;

  private String balance;

  private String currency;

  private String createdTimestamp;

  private String updatedTimestamp;

}
