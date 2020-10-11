package com.hackingrio.hackriooceanos2020maritimos.repositories;

import com.hackingrio.hackriooceanos2020maritimos.domain.entities.Event;
import com.hackingrio.hackriooceanos2020maritimos.domain.enums.ReportCategoryEnum;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
  List<Event> findByCategory(ReportCategoryEnum category);

  List<Event> findByDate(Date date);
}
