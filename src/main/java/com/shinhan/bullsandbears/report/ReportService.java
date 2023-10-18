package com.shinhan.bullsandbears.report;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReportService {
  ReportDto.CreateResponse createReport(ReportDto.CreateRequest request, Long userId);
  ReportDto.SearchResponse searchReport(Long reportId);
  ReportDto.UserSearchResponse findReportByUser(Long userId);
}
