package com.hackingrio.hackriooceanos2020maritimos.services;

import com.hackingrio.hackriooceanos2020maritimos.config.exceptions.ApiException;
import com.hackingrio.hackriooceanos2020maritimos.config.exceptions.ApiMessageEnum;
import com.hackingrio.hackriooceanos2020maritimos.domain.entities.Event;
import com.hackingrio.hackriooceanos2020maritimos.domain.enums.ReportCategoryEnum;
import com.hackingrio.hackriooceanos2020maritimos.repositories.EventRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventServiceImpl implements EventService {
  private static EventRepository eventRepository;

  @Autowired
  EventServiceImpl(final EventRepository eventRepository) {
    EventServiceImpl.eventRepository = eventRepository;
  }

  @Override
  public Optional<Event> find(String id) {
    Optional.ofNullable(id)
        .orElseThrow(
            () -> {
              log.error("Parameter EVENT_ID is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "EVENT_ID");
            });
    try {
      log.debug("querying event[id={}]", id);
      return eventRepository.findById(id);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApiException(
          ApiMessageEnum.ERROR_RESOURCE_NOT_FOUND, e, String.format("Event[id=%d]", id));
    }
  }

  @Override
  public List<Event> findAllByCategory(ReportCategoryEnum category) {
    Optional.ofNullable(category)
        .orElseThrow(
            () -> {
              log.error("Parameter EVENT_CATEGORY is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "EVENT_CATEGORY");
            });
    try {
      log.debug("querying all events[category={}]", category.name());
      return eventRepository.findByCategory(category);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApiException(
          ApiMessageEnum.ERROR_RESOURCE_NOT_FOUND,
          e,
          String.format("Events[category=%s]", category.name()));
    }
  }

  @Override
  public List<Event> findAllByDate(Date date) {
    Optional.ofNullable(date)
        .orElseThrow(
            () -> {
              log.error("Parameter EVENT_DATE is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "EVENT_DATE");
            });
    try {
      log.debug("querying all events[date={}]", date.toString());
      return eventRepository.findByDate(date);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApiException(
          ApiMessageEnum.ERROR_RESOURCE_NOT_FOUND,
          e,
          String.format("Events[date=%s]", date.toString()));
    }
  }

  @Override
  public List<Event> findAll() {
    try {
      log.debug("querying all events");
      return eventRepository.findAll();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApiException(ApiMessageEnum.ERROR_RESOURCE_NOT_FOUND, e, "all events");
    }
  }

  @Override
  public Event save(Event event) {
    Optional.ofNullable(event)
        .orElseThrow(
            () -> {
              log.error("Parameter event is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "event");
            });
    try {

      log.debug("creating event {}}", event.toString());
      return eventRepository.save(event);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      log.error(e.toString());
      throw new ApiException(ApiMessageEnum.ERROR_ON_SAVE_ENTITY, e, event.toString());
    }
  }

  @Override
  public void update(Event event) {
    Optional.ofNullable(event)
        .orElseThrow(
            () -> {
              log.error("Parameter event is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "event");
            });

    Optional.ofNullable(event.getId())
        .orElseThrow(
            () -> {
              log.error("EVENT_ID parameter is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "EVENT_ID");
            });

    try {

      log.debug("updating event {}", event.toString());

      eventRepository.save(event);

      log.debug("updated event");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      log.error(event.toString());
      throw new ApiException(ApiMessageEnum.ERROR_ON_SAVE_ENTITY, e, event.toString());
    }
  }

  @Override
  public void delete(String id) {
    Optional.ofNullable(id)
        .orElseThrow(
            () -> {
              log.error("EVENT_ID parameter is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "EVENT_ID");
            });

    if (!eventRepository.existsById(id)) {
      log.error("Event[id={}] do not exist", id);
      throw new ApiException(
          ApiMessageEnum.ERROR_RESOURCE_NOT_FOUND, String.format("Event[id=%d]", id));
    }

    try {

      log.debug("deleting event [id={}]", id);
      eventRepository.deleteById(id);
      log.debug("deleted user");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApiException(
          ApiMessageEnum.ERROR_ON_DELETE_ENTITY, e, String.format("Event[id=%d]", id));
    }
  }
}
