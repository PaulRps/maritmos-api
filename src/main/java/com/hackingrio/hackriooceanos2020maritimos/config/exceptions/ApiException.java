package com.hackingrio.hackriooceanos2020maritimos.config.exceptions;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

  private ApiMessageEnum customMessage;
  private Throwable realException;
  private String complement;

  public ApiException(
      final ApiMessageEnum customMessage, final Throwable realException, final String complement) {
    super(realException.getMessage());
    this.customMessage = customMessage;
    this.realException = realException;
    this.complement = complement;
  }

  public ApiException(final ApiMessageEnum customMessage, final Throwable realException) {
    super(customMessage.getMessage());
    this.customMessage = customMessage;
    this.realException = realException;
  }

  public ApiException(final ApiMessageEnum customMessage, final String complement) {
    super(complement);
    this.customMessage = customMessage;
    this.complement = complement;
  }

  public ApiException() {}
}
