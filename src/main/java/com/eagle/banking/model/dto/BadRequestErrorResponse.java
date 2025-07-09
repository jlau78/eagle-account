package com.eagle.banking.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BadRequestErrorResponse implements Response {

  private String message;

  private String details;

}
