package com.hackingrio.hackriooceanos2020maritimos.config.exceptions;

import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ApiMessageHandlerImpl implements ApiMessageHandler {

  @Override
  public String getMessage(ApiException ex) {
    if (ex.getCustomMessage().getMessage().contains(ApiMessageEnum.COMPLEMENT_TOKEN)) {
      return getMessage(ex.getCustomMessage(), ex.getComplement());
    }
    return ex.getCustomMessage().getMessage();
  }

  @Override
  public String getMessage(ApiMessageEnum errorEnum, String complement) {
    return errorEnum.getMessage().replace(ApiMessageEnum.COMPLEMENT_TOKEN, complement);
  }

  @Override
  public String getDetail(Throwable rootException) {
    return Optional.ofNullable(rootException)
        .map(ex -> String.format("%s: %s", ex.getClass().toString(), ex.getMessage()))
        .orElse("");
  }
}
