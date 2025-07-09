package com.eagle.banking.model.dto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionResponse {

  private String id;
  private String amount;
  private String currency;
  private String type;
  private String reference;
  private String userId;
  private String createdTimestamp;

}
