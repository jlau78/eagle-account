package com.eagle.banking.controller;

import com.eagle.banking.advice.ServiceException;
import com.eagle.banking.client.AuthClient;
import com.eagle.banking.model.dto.BankAccountResponse;
import com.eagle.banking.model.dto.CreateBankAccountRequest;
import com.eagle.banking.model.dto.ErrorResponse;
import com.eagle.banking.model.dto.Response;
import com.eagle.banking.model.dto.UpdateBankAccountRequest;
import com.eagle.banking.service.AccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

  private final AccountsService accountsService;

  private final AuthClient authClient;

  @PostMapping
  public ResponseEntity<Response> createAccount(@RequestHeader(required = true) String token,
                                                @Valid @RequestBody CreateBankAccountRequest account) {
    ResponseEntity<Response> response = null;

    try {
      BankAccountResponse accountResponse = accountsService.createAccount(account);

      if (accountResponse != null) {
        response = ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);

      } else {
        response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.builder()
                .message("Account creation failed")
                .details("Unable to create account at this time").build());
      }

    } catch (ServiceException e) {
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
          .message("Internal Service Error")
          .details("An error has occurred creating a user").build());
    }

    return response;
  }

  @GetMapping("/{accountNumber}")
  public ResponseEntity<Response> getAccount(@RequestHeader(required = true) String token,
                                             @Pattern(regexp = "^01\\d{6}$", message = "Invalid account number format")
                                             @PathVariable("accountNumber") String accountNumber) {
    ResponseEntity<Response> response = null;

    try {

      response = authClient.validateToken(token, accountNumber);
      if (Objects.nonNull(response)) {
        return response;
      }

      BankAccountResponse accountResponse = accountsService.getAccountById(accountNumber);

      if (accountResponse != null) {
        response = ResponseEntity.ok(accountResponse);
      } else {
        response = ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.builder()
                .message("Account not found")
                .details("No account found with the provided ID").build());
      }

    } catch (ServiceException e) {
      log.error("Error retrieving account: {}", e.getMessage());
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
          .message("Internal Service Error")
          .details("An error has occurred retrieving the account").build());
    }

    return response;
  }

  @PatchMapping("/{accountNumber}")
  public ResponseEntity<Response> updateAccount(@RequestHeader(required = true) String token,
                                                @Pattern(regexp = "^01\\d{6}$", message = "Invalid account number format")
                                                @PathVariable("accountNumber") String accountNumber,
                                                @RequestBody UpdateBankAccountRequest account) {
    ResponseEntity<Response> response = null;

    try {

      response = authClient.validateToken(token, accountNumber);
      if (Objects.nonNull(response)) {
        return response;
      }

      // Assuming updateAccount method exists in AccountsService
      BankAccountResponse accountResponse = accountsService.updateAccount(accountNumber, account);

      if (accountResponse != null) {
        response = ResponseEntity.ok(accountResponse);
      } else {
        response = ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.builder()
                .message("Account not found")
                .details("No account found with the provided ID").build());
      }

    } catch (ServiceException e) {
      log.error("Error updating account: {}", e.getMessage());
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
          .message("Internal Service Error")
          .details("An error has occurred updating the account").build());
    }

    return response;
  }


}
