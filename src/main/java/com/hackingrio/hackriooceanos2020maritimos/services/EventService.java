package com.hackingrio.hackriooceanos2020maritimos.services;

import com.hackingrio.hackriooceanos2020maritimos.domain.entities.Event;
import com.hackingrio.hackriooceanos2020maritimos.domain.enums.ReportCategoryEnum;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EventService {
  Optional<Event> find(String userId);

  List<Event> findAllByCategory(ReportCategoryEnum category);

  List<Event> findAllByDate(Date date);

  List<Event> findAll();

  Event save(Event u);

  void update(Event u);

  void delete(String id);
}
