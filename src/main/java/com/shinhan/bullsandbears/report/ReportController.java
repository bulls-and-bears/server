package com.shinhan.bullsandbears.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReportController {
  private final ReportService reportService;

  @PostMapping("/report")
  public ResponseEntity<ReportDto.CreateResponse> createReport(@RequestBody ReportDto.CreateRequest reportDto, @RequestParam String name) {
    return ResponseEntity.status(HttpStatus.OK).body(reportService.createReport(reportDto, name));
  }

  @GetMapping("/report/{reportId}")
  public ResponseEntity<ReportDto.SearchResponse> searchReport(@PathVariable("reportId") Long reportId) {
    return ResponseEntity.status(HttpStatus.OK).body(reportService.searchReport(reportId));
  }

  @GetMapping("/mypage")
  public ResponseEntity<ReportDto.UserSearchResponse> searchReportByUser(@RequestParam String name) {
    return ResponseEntity.status(HttpStatus.OK).body(reportService.findReportByUser(name));
  }
}
