package com.hackingrio.hackriooceanos2020maritimos.config.exceptions;

import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static ApiExceptionService apiExceptionService;

  @Autowired
  GlobalExceptionHandler(final ApiExceptionService apiExceptionService) {
    GlobalExceptionHandler.apiExceptionService = apiExceptionService;
  }

  @ExceptionHandler(value = {ApiException.class})
  ResponseEntity<ApiError> defaultHandle(ApiException ex, ServletWebRequest request) {
    return apiExceptionService.handlePeladatorException(ex, request);
  }

  @ExceptionHandler({
    ServletException.class,
    AccessDeniedException.class,
    BadCredentialsException.class,
    HttpClientErrorException.class,
    HttpServerErrorException.class,
    IllegalArgumentException.class,
    Exception.class
  })
  protected ResponseEntity<ApiError> handleConflict(Throwable ex, ServletWebRequest request) {
    try {
      ResponseEntity<Object> objectResponseEntity = this.handleException((Exception) ex, request);
      return apiExceptionService.handleDefaultException(objectResponseEntity, ex, request);
    } catch (Exception e) {
      return apiExceptionService.handleDefaultException(e, request);
    }
    //    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
