package gladiator.philosopher.temp.security;

import lombok.Data;

@Data
public class ErrorDto {

  private int code;
  private String message;

  public ErrorDto(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
