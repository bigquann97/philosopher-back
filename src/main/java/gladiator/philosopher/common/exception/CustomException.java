package gladiator.philosopher.common.exception;

import gladiator.philosopher.common.exception.dto.ExceptionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {

  private final ExceptionStatus exceptionStatus;

}
