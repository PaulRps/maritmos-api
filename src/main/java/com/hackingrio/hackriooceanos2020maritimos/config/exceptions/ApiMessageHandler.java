package com.hackingrio.hackriooceanos2020maritimos.config.exceptions;

public interface ApiMessageHandler {
  String getMessage(ApiException ex);

  String getMessage(ApiMessageEnum errorEnum, String complement);

  String getDetail(Throwable rootException);
}
