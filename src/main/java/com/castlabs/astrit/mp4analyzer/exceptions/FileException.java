package com.castlabs.astrit.mp4analyzer.exceptions;

import static java.lang.String.format;

import org.springframework.http.HttpStatus;

public class FileException extends RestException {

  public FileException(String message, HttpStatus httpStatus) {
    super(message, httpStatus);
  }
  
  public static FileException fileDownloadError(String url) {
    throw new FileException(format("File download error: %s", url), HttpStatus.INTERNAL_SERVER_ERROR);
  } 
  
  public static FileException fileAnalyzeError(String url) {
    throw new FileException(format("An error occurred while analyzing the mp4 file %s", url), HttpStatus.INTERNAL_SERVER_ERROR);
  }
  public static FileException notFound(String url) {
    throw new FileException(format("File not found: %s", url), HttpStatus.NOT_FOUND);
  }
}
