
package com.castlabs.astrit.mp4analyzer.config;

import com.castlabs.astrit.mp4analyzer.exceptions.RestException;
import java.time.Instant;
import java.util.stream.Collectors;
import lombok.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler({RestException.class})
  public ResponseEntity<ErrorResponse> badRequestException(RestException restException) {
    return createResponse(restException.getMessage(), restException.getHttpStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    String fieldErrors = ex.getBindingResult().getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", "));
    return createResponse(fieldErrors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ErrorResponse> exception(RuntimeException invalidSignature) {
    return createResponse(invalidSignature.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorResponse> createResponse(String message, HttpStatus httpStatus) {
    ErrorResponse body = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), message, Instant.now());
    return ResponseEntity.status(httpStatus).body(body);
  }


  @Value
  private static class ErrorResponse {

    int code;
    String status;
    String message;
    Instant timeStamp;
  }
}
