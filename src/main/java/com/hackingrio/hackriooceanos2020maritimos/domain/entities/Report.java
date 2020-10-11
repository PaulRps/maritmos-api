package com.hackingrio.hackriooceanos2020maritimos.domain.entities;

import com.hackingrio.hackriooceanos2020maritimos.domain.enums.ReportCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class Report {

  @Id private String id;
  private ReportCategoryEnum category;
  private String description;
  private String latitude;
  private String longitude;
  private String userId;
  private String image;
  //  private Binary image;
}
