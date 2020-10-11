package com.hackingrio.hackriooceanos2020maritimos.controllers;

import com.hackingrio.hackriooceanos2020maritimos.domain.dto.ReportDto;
import com.hackingrio.hackriooceanos2020maritimos.services.ReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
    origins = {"*"},
    maxAge = 3600)
@RestController
@RequestMapping("report")
public class ReportController {

  private static ReportService reportService;

  @Autowired
  ReportController(final ReportService reportService) {
    ReportController.reportService = reportService;
  }

  @GetMapping("{id}")
  ResponseEntity<ReportDto> getById(@PathVariable String id) {
    return ResponseEntity.ok(reportService.find(id));
  }

  @GetMapping
  ResponseEntity<List<ReportDto>> getAll() {
    return ResponseEntity.ok(reportService.findAll());
  }

  @PostMapping
  ResponseEntity<ReportDto> save(@RequestBody ReportDto user) {
    return new ResponseEntity<>(reportService.save(user), HttpStatus.CREATED);
  }

  @PutMapping
  ResponseEntity<Void> update(@RequestBody ReportDto user) {
    reportService.update(user);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("{id}")
  ResponseEntity<Void> delete(@PathVariable String id) {
    reportService.delete(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
