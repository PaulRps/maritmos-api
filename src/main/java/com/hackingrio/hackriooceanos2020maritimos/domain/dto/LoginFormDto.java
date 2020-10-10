package com.hackingrio.hackriooceanos2020maritimos.domain.dto;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class LoginFormDto {

  private String userName;
  private String password;

  public UsernamePasswordAuthenticationToken convert() {
    return new UsernamePasswordAuthenticationToken(this.userName, this.password);
  }
}
