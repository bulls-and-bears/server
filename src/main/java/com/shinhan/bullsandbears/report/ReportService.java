package com.shinhan.bullsandbears.report;

import org.springframework.stereotype.Service;

@Service
public interface ReportService {
  ReportDto.CreateResponse createReport(ReportDto.CreateRequest request);
  ReportDto.SearchResponse searchReport(Long reportId);
}
