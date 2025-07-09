package com.eagle.banking.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ListTransactionsResponse {

  List<TransactionResponse> transactions;
}
