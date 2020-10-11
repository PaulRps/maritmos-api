package com.hackingrio.hackriooceanos2020maritimos.services;

import com.hackingrio.hackriooceanos2020maritimos.config.exceptions.ApiException;
import com.hackingrio.hackriooceanos2020maritimos.config.exceptions.ApiMessageEnum;
import com.hackingrio.hackriooceanos2020maritimos.domain.dto.ReportDto;
import com.hackingrio.hackriooceanos2020maritimos.domain.entities.Report;
import com.hackingrio.hackriooceanos2020maritimos.repositories.ReportRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

  private static ReportRepository reportRepository;

  @Autowired
  ReportServiceImpl(final ReportRepository reportRepository) {
    ReportServiceImpl.reportRepository = reportRepository;
  }

  @Override
  public ReportDto find(String id) {
    Optional.ofNullable(id)
        .orElseThrow(
            () -> {
              log.error("Parameter REPORT_ID is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "REPORT_ID");
            });

    try {
      log.debug("querying report[id={}]", id);
      return reportRepository.findById(id).map(this::toDto).orElseThrow(() -> new ApiException());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApiException(
          ApiMessageEnum.ERROR_RESOURCE_NOT_FOUND, e, String.format("report[id=%d]", id));
    }
  }

  @Override
  public List<ReportDto> findAll() {
    try {
      log.debug("querying all reports");
      return reportRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApiException(ApiMessageEnum.ERROR_INTERNAL_SERVER, e, "all reports");
    }
  }

  @Override
  public ReportDto save(ReportDto report) {
    Optional.ofNullable(report)
        .orElseThrow(
            () -> {
              log.error("Parameter report is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "report");
            });
    try {

      log.debug("creating report {}}", report.toString());
      Report saved = reportRepository.save(toEntity(report));
      report.setId(saved.getId());
      return report;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      log.error(report.toString());
      throw new ApiException(ApiMessageEnum.ERROR_ON_SAVE_ENTITY, e, report.toString());
    }
  }

  @Override
  public void update(ReportDto report) {
    Optional.ofNullable(report)
        .orElseThrow(
            () -> {
              log.error("Parameter report is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "report");
            });

    Optional.ofNullable(report.getId())
        .orElseThrow(
            () -> {
              log.error("REPORT_ID parameter is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "REPORT_ID");
            });

    try {

      log.debug("updating report {}", report.toString());

      reportRepository.save(toEntity(report));

      log.debug("updated report");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      log.error(report.toString());
      throw new ApiException(ApiMessageEnum.ERROR_ON_SAVE_ENTITY, e, report.toString());
    }
  }

  @Override
  public void delete(String id) {
    Optional.ofNullable(id)
        .orElseThrow(
            () -> {
              log.error("USER_ID parameter is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "REPORT_ID");
            });

    if (!reportRepository.existsById(id)) {
      log.error("Report[id={}] do not exist", id);
      throw new ApiException(
          ApiMessageEnum.ERROR_RESOURCE_NOT_FOUND, String.format("Report[id=%d]", id));
    }

    try {

      log.debug("deleting report [id={}]", id);
      reportRepository.deleteById(id);
      log.debug("deleted report");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApiException(
          ApiMessageEnum.ERROR_ON_DELETE_ENTITY, e, String.format("Report[id=%d]", id));
    }
  }

  private Report toEntity(ReportDto report) {
    try {
      //      Binary binaryImg = null;
      //      if (Objects.nonNull(report.getImage())) {
      //        binaryImg = new Binary(BsonBinarySubType.BINARY, report.getImage().getBytes());
      //      }
      return Report.builder()
          .description(report.getDescription())
          .latitude(report.getLatitude())
          .longitude(report.getLongitude())
          .userId(report.getUserId())
          .image(report.getImage())
          .build();
    } catch (IOException e) {
      log.error("Error on read image bytes", e);
      throw new ApiException(ApiMessageEnum.ERROR_READ_BYTES_FROM, e, "REPORTDTO_IMAGE");
    }
  }

  private ReportDto toDto(Report report) {
    //    MultipartFile binaryImg = null;
    //    if (Objects.nonNull(report.getImage())) {
    //      binaryImg = new MultipartFileImage(report.getImage().getData());
    //    }
    return ReportDto.builder()
        .id(report.getId())
        .category(report.getCategory())
        .description(report.getDescription())
        .latitude(report.getLatitude())
        .longitude(report.getLongitude())
        .userId(report.getUserId())
        .image(report.getImage())
        .build();
  }
}
