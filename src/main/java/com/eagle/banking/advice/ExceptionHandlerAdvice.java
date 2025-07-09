package com.eagle.banking.advice;

import com.eagle.banking.model.dto.ErrorResponse;
import com.eagle.banking.model.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerAdvice {

  public static final String MSG_FORBIDDEN_ACCESS = "Forbidden access";

  public static final String MSG_UNAUTHORIZED_ACCESS = "Unauthorized access";

  public static final String MSG_VALIDATION_ERRORS = "Validation Errors";

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handleValidationException(MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getFieldErrors().forEach((error) -> {
      String field = ((FieldError) error).getField();
      String errorMsg = (error.getDefaultMessage());
      errors.put(field, errorMsg);
    });

    return new ResponseEntity(ErrorResponse.builder()
        .message(MSG_VALIDATION_ERRORS)
        .details(errors.entrySet().stream()
            .map((entry) -> entry.getKey()
                .concat(":").concat(entry.getValue().concat(",")))
            .collect(Collectors.joining())
        )
        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ForbiddenAccessException.class)
  public ResponseEntity<Response> handleForbiddenAccessException(ForbiddenAccessException ex) {
    return ResponseEntity
        .of(Optional.of(ErrorResponse.builder()
            .message(MSG_FORBIDDEN_ACCESS)
            .details(ex.getMessage())
            .build()))
        .status(HttpStatus.FORBIDDEN).build();

  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Response> handleUnauthorizedException(UnauthorizedException ex) {
    return ResponseEntity
        .of(Optional.of(ErrorResponse.builder()
            .message(MSG_UNAUTHORIZED_ACCESS)
            .details(ex.getMessage())
            .build()))
        .status(HttpStatus.UNAUTHORIZED).build();
  }

}
