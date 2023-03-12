package gladiator.philosopher.common.exception;

import gladiator.philosopher.common.exception.dto.ExceptionStatus;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

  private final int code;
  private final String message;

  public AuthException(ExceptionStatus status) {
    this.code = status.getStatusCode();
    this.message = status.getMessage();
  }

}
