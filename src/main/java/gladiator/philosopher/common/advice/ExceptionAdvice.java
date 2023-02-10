package gladiator.philosopher.common.advice;

import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.exception.DuplicatedException;
import gladiator.philosopher.common.exception.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

  @ExceptionHandler({CustomException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorDto handleCustomException(CustomException ex) {
    log.error(ex.getMessage());
    return new ErrorDto(ex.getExceptionStatus().getStatusCode(),
        ex.getExceptionStatus().getMessage());
  }

  @ExceptionHandler({DuplicatedException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorDto duplicatedException(DuplicatedException e) {
    e.printStackTrace();
    log.error(e.getMessage());
    return new ErrorDto(e.getCode(), e.getMessage());
  }
  
}
