package com.hackingrio.hackriooceanos2020maritimos.services;

import com.hackingrio.hackriooceanos2020maritimos.domain.dto.ReportDto;
import java.util.List;

public interface ReportService {

  ReportDto find(String id);

  List<ReportDto> findAll();

  ReportDto save(ReportDto report);

  void update(ReportDto report);

  void delete(String id);
}
