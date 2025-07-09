package com.eagle.banking.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse implements Response {

  private String message;

  private String details;

}
