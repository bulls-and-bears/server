package com.shinhan.bullsandbears.report;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReportService {
  ReportDto.CreateResponse createReport(ReportDto.CreateRequest request, String userName);
  ReportDto.SearchResponse searchReport(Long reportId);
  ReportDto.UserSearchResponse findReportByUser(String userName);
}
