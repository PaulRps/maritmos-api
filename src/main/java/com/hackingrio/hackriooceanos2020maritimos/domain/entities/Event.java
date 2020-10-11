package com.hackingrio.hackriooceanos2020maritimos.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hackingrio.hackriooceanos2020maritimos.domain.enums.ReportCategoryEnum;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class Event {
  private @Id String id;
  private String description;
  private ReportCategoryEnum category;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
  private Date date;

  private String local;
  private List<String> users;
}
