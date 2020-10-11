package com.hackingrio.hackriooceanos2020maritimos.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReportCategoryEnum {
  LIXO_PRAIA("Lixo na praia"),
  POLUICAO_MAR("Poluição no mar");

  private String description;
}
