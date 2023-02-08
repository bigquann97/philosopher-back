package gladiator.philosopher.common.exception;

import lombok.Data;

@Data
public class ErrorDto {

  private final int statusCode;
  private final String message;

}
