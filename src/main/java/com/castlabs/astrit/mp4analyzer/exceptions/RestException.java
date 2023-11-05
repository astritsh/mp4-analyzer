package com.castlabs.astrit.mp4analyzer.exceptions;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {

  private HttpStatus httpStatus;

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public RestException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
