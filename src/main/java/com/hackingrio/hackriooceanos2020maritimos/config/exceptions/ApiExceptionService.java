package com.hackingrio.hackriooceanos2020maritimos.config.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

public interface ApiExceptionService {
  ResponseEntity<ApiError> handleDefaultException(Exception ex, ServletWebRequest request);

  ResponseEntity<ApiError> handlePeladatorException(ApiException ex, ServletWebRequest request);

  ResponseEntity<ApiError> handleDefaultException(
      ResponseEntity<Object> objectResponseEntity, Throwable ex, ServletWebRequest request);
}
