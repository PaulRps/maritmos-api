package com.hackingrio.hackriooceanos2020maritimos.domain.dto;

import com.hackingrio.hackriooceanos2020maritimos.domain.enums.ReportCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
  private String id;
  private String description;
  private ReportCategoryEnum category;
  private String latitude;
  private String longitude;
  private String userId;
  private String image;
  //  private MultipartFile image;
}
