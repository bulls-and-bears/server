package com.shinhan.bullsandbears.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController {
  private final ReportService reportService;

  @PostMapping("/")
  public ResponseEntity<ReportDto.CreateResponse> createReport(@RequestBody ReportDto.CreateRequest reportDto) {
    return ResponseEntity.status(HttpStatus.OK).body(reportService.createReport(reportDto));
  }

  @GetMapping("/{reportId}")
  public ResponseEntity<ReportDto.SearchResponse> searchReport(@PathVariable("reportId") Long reportId) {
    return ResponseEntity.status(HttpStatus.OK).body(reportService.searchReport(reportId));
  }
}
