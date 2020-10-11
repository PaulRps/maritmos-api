package com.hackingrio.hackriooceanos2020maritimos.repositories;

import com.hackingrio.hackriooceanos2020maritimos.domain.entities.Report;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report, String> {
  Optional<Report> findById(String id);
}
