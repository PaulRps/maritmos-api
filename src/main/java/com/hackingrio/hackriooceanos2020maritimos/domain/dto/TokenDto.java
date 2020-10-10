package com.hackingrio.hackriooceanos2020maritimos.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
  private String value;
  private String type;
  private String id;
  //  private RoleEnum role;
}
