package gladiator.philosopher.common.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

  @ExceptionHandler({CustomException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity handleCustomException(CustomException ex) {
    return new ResponseEntity(
        new ErrorDto(ex.getExceptionStatus().getStatusCode(), ex.getExceptionStatus().getMessage()),
        HttpStatus.valueOf(ex.getExceptionStatus().getStatusCode()));
  }

}
