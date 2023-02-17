package gladiator.philosopher.common.advice;

import gladiator.philosopher.common.exception.AuthException;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.exception.DuplicatedException;
import gladiator.philosopher.common.exception.FileException;
import gladiator.philosopher.common.exception.InvalidAccessException;
import gladiator.philosopher.common.exception.NotFoundException;
import gladiator.philosopher.common.exception.dto.ErrorDto;
import java.io.IOException;
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

  @ExceptionHandler({AuthException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorDto authException(AuthException ex) {
    log.error(ex.getMessage());
    return new ErrorDto(ex.getCode(), ex.getMessage());
  }

  // TODO: 2023/02/17 제거 요망
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

  @ExceptionHandler({FileException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorDto fileException(FileException e) {
    e.printStackTrace();
    log.error(e.getMessage());
    return new ErrorDto(e.getCode(), e.getMessage());
  }

  @ExceptionHandler({InvalidAccessException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorDto InvalidAccessException(InvalidAccessException e) {
    e.printStackTrace();
    log.error(e.getMessage());
    return new ErrorDto(e.getCode(), e.getMessage());
  }

  @ExceptionHandler({NotFoundException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorDto NotFoundException(NotFoundException e) {
    e.printStackTrace();
    log.error(e.getMessage());
    return new ErrorDto(e.getCode(), e.getMessage());
  }

  // ========= 커스텀 익셉션 외 예외 처리 ==========

  @ExceptionHandler({IOException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorDto IoStreamException(CustomException ex) {
    ex.printStackTrace();
    log.error(ex.getMessage());
    return new ErrorDto(ex.getExceptionStatus().getStatusCode(),
        ex.getExceptionStatus().getMessage());
  }

}
