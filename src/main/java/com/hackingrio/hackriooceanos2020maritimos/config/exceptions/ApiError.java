package com.hackingrio.hackriooceanos2020maritimos.config.exceptions;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
  private Date timestamp;
  private Integer status;
  private String message;
  private String detail;
  private String uri;
}
