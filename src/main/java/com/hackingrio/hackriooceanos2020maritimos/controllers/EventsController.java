package com.hackingrio.hackriooceanos2020maritimos.controllers;

import com.hackingrio.hackriooceanos2020maritimos.domain.entities.Event;
import com.hackingrio.hackriooceanos2020maritimos.domain.enums.ReportCategoryEnum;
import com.hackingrio.hackriooceanos2020maritimos.services.EventService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("event")
public class EventsController {

  private static EventService eventService;

  @Autowired
  EventsController(final EventService eventService) {
    EventsController.eventService = eventService;
  }

  @GetMapping("{id}")
  ResponseEntity<Event> getById(@PathVariable String id) {
    return ResponseEntity.ok(eventService.find(id).get());
  }

  @GetMapping
  ResponseEntity<List<Event>> getAll() {
    return ResponseEntity.ok(eventService.findAll());
  }

  @GetMapping("category/{category}")
  ResponseEntity<List<Event>> getByCategory(@PathVariable ReportCategoryEnum category) {
    return ResponseEntity.ok(eventService.findAllByCategory(category));
  }

  @GetMapping("date/{date}")
  ResponseEntity<List<Event>> getByDate(@PathVariable Date date) {
    return ResponseEntity.ok(eventService.findAllByDate(date));
  }

  @PostMapping
  ResponseEntity<Event> save(@RequestBody Event event) {
    return new ResponseEntity<>(eventService.save(event), HttpStatus.CREATED);
  }

  @PutMapping
  ResponseEntity<Void> update(@RequestBody Event event) {
    eventService.update(event);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("{id}")
  ResponseEntity<Void> delete(@PathVariable String id) {
    eventService.delete(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
