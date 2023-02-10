package gladiator.philosopher.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDto {

  private final int statusCode;
  private final String message;

}
