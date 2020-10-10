package com.hackingrio.hackriooceanos2020maritimos.config.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiMessageEnum {
  ERROR_PARAMETER_NOT_PRESENT("Parameter {0} not present", HttpStatus.BAD_REQUEST),
  ERROR_RESOURCE_NOT_FOUND("Resource {0} not found", HttpStatus.BAD_REQUEST),
  ERROR_ON_SAVE_ENTITY("Error on save entity {0}", HttpStatus.INTERNAL_SERVER_ERROR),
  ERROR_ON_DELETE_ENTITY("Error on delete entity {0}", HttpStatus.INTERNAL_SERVER_ERROR),
  ERROR_INTERNAL_SERVER("Internal server error on {0}", HttpStatus.INTERNAL_SERVER_ERROR),
  ERROR_JWT_AUTH("Error to get JWT token to user {0}", HttpStatus.BAD_REQUEST);

  private String message;
  private HttpStatus status;

  private ApiMessageEnum(final String message, final HttpStatus status) {
    this.message = message;
    this.status = status;
  }

  public static final String COMPLEMENT_TOKEN = "{0}";
}
