package com.eagle.banking.client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eagle.banking.advice.ForbiddenAccessException;
import com.eagle.banking.advice.UnauthorizedException;
import com.eagle.banking.model.dto.ErrorResponse;
import com.eagle.banking.model.dto.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.eagle.banking.advice.ExceptionHandlerAdvice.*;

@Component
public class AuthClient {

  private static final String ACCOUNT_ID = "accountId";

  public boolean isAllowedAccess(String token, String accountId) {
    if (StringUtils.isBlank(token)) {
      return false;
    } else {
      DecodedJWT decoded = JWT.decode(token);

      // Assume accountId is stored in the token for simplicity
      return accountId.equals(decoded.getClaim(ACCOUNT_ID));
    }
  }

  public boolean isValid(String token) {
    if (StringUtils.isBlank(token)) {
      return false;
    } else {
      DecodedJWT decoded = JWT.decode(token);

      // Just checking expired token for now
      return decoded.getExpiresAt().toInstant().isBefore(LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC));
    }
  }

  public ResponseEntity<Response> validateToken(String token, String accountId) {
    if (!isValid(token)) {
      return new ResponseEntity(
          ErrorResponse.builder()
              .message(MSG_UNAUTHORIZED_ACCESS)
              .details("The auth token is not valid")
              .build(), HttpStatus.FORBIDDEN);
    }

    if (!isAllowedAccess(token, accountId)) {
      return new ResponseEntity(
          ErrorResponse.builder()
              .message(MSG_FORBIDDEN_ACCESS)
              .details("Forbidden access to this resource")
              .build(), HttpStatus.FORBIDDEN);
    }


    return null;
  }

  /**
   * @depreated Temporarily disabled for easier unit testing
   */
  public void validateTokenWithException(String token, String accountId) throws ForbiddenAccessException, UnauthorizedException {
    boolean isValidUser = true;
    boolean isValidToken = false;
    if (StringUtils.isNotBlank(token)) {
      DecodedJWT decoded = JWT.decode(token);

      // Assume accountId is stored in the token for simplicity
      isValidUser = !accountId.equals(decoded.getClaim(ACCOUNT_ID));

      isValidToken = isValidToken(decoded);
    }

    if (!isValidToken) {
      throw new UnauthorizedException("The auth token is not valid");
    }

    if (!isValidUser) {
      throw new ForbiddenAccessException("You are forbidden to perform this function");
    }
  }

  private boolean isValidToken(DecodedJWT decoded) {
    return decoded.getExpiresAt().toInstant().isBefore(LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC));
  }

}
