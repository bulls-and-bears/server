package com.shinhan.bullsandbears.report;

import com.shinhan.bullsandbears.domain.StockReportHistory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


public class StockReportDto {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class CreateResponse {
    private Long reportId;
    private Long stockId;
    private LocalDate createdAt;

    public CreateResponse(StockReportHistory stockReportHistory) {
      this.reportId = stockReportHistory.getReport().getId();
      this.stockId = stockReportHistory.getStockMaster().getId();
      this.createdAt = stockReportHistory.getReport().getCreatedAt();
    }
  }
}
