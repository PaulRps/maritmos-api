package com.hackingrio.hackriooceanos2020maritimos.config.exceptions;

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;

@Service
public class ApiExceptionServiceImpl implements ApiExceptionService {
  private static ApiMessageHandler peladatorMessageHandler;

  @Autowired
  ApiExceptionServiceImpl(final ApiMessageHandler peladatorMessageHandler) {
    ApiExceptionServiceImpl.peladatorMessageHandler = peladatorMessageHandler;
  }

  @Override
  public ResponseEntity<ApiError> handleDefaultException(Exception ex, ServletWebRequest request) {
    return new ResponseEntity<>(
        ApiError.builder()
            .timestamp(new Date())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message(
                peladatorMessageHandler.getMessage(ApiMessageEnum.ERROR_INTERNAL_SERVER, "api"))
            .detail(peladatorMessageHandler.getDetail(getRootCause(ex)))
            .uri(request.getRequest().getRequestURI())
            .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  public ResponseEntity<ApiError> handlePeladatorException(
      ApiException ex, ServletWebRequest request) {
    return new ResponseEntity<>(
        ApiError.builder()
            .timestamp(new Date())
            .status(ex.getCustomMessage().getStatus().value())
            .message(peladatorMessageHandler.getMessage(ex))
            .detail(peladatorMessageHandler.getDetail(getRootCause(ex.getRealException())))
            .uri(request.getRequest().getRequestURI())
            .build(),
        ex.getCustomMessage().getStatus());
  }

  @Override
  public ResponseEntity<ApiError> handleDefaultException(
      ResponseEntity<Object> objectResponseEntity, Throwable ex, ServletWebRequest request) {

    if (ApiException.class.isInstance(ex)) {
      ApiException peladatorException = (ApiException) ex;
      return new ResponseEntity<>(
          ApiError.builder()
              .timestamp(new Date())
              .status(peladatorException.getCustomMessage().getStatus().value())
              .uri(request.getRequest().getRequestURI())
              .message(peladatorMessageHandler.getMessage(peladatorException))
              .detail(peladatorMessageHandler.getDetail(getRootCause(ex)))
              .build(),
          objectResponseEntity.getHeaders(),
          peladatorException.getCustomMessage().getStatus());
    } else {
      return new ResponseEntity<>(
          ApiError.builder()
              .timestamp(new Date())
              .status(objectResponseEntity.getStatusCode().value())
              .uri(request.getRequest().getRequestURI())
              .message(
                  peladatorMessageHandler.getMessage(ApiMessageEnum.ERROR_INTERNAL_SERVER, "api"))
              .detail(peladatorMessageHandler.getDetail(getRootCause(ex)))
              .build(),
          objectResponseEntity.getHeaders(),
          objectResponseEntity.getStatusCode());
    }
  }

  private Throwable getRootCause(Throwable ex) {
    return Optional.ofNullable(ex)
        .map(
            e -> {
              Throwable rootCause = e;
              while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
                rootCause = rootCause.getCause();
              }
              return rootCause;
            })
        .orElse(ex);
  }
}
