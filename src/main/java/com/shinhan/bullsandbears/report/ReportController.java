package com.shinhan.bullsandbears.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController {
  private final ReportService reportService;

  @PostMapping("/{userId}")
  public ResponseEntity<ReportDto.CreateResponse> createReport(@RequestBody ReportDto.CreateRequest reportDto, @PathVariable("userId") Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(reportService.createReport(reportDto, userId));
  }

  @GetMapping("/{reportId}")
  public ResponseEntity<ReportDto.SearchResponse> searchReport(@PathVariable("reportId") Long reportId) {
    return ResponseEntity.status(HttpStatus.OK).body(reportService.searchReport(reportId));
  }

  @GetMapping("/mypage/{userId}")
  public ResponseEntity<List<ReportDto.SearchResponse>> searchReportByUser(@PathVariable("userId") Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(reportService.findReportByUser(userId));
  }
}
