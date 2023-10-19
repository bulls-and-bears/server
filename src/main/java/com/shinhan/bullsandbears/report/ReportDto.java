package com.shinhan.bullsandbears.report;
import com.shinhan.bullsandbears.stock.StockDto;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReportDto {
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class CreateRequest {
    private BigDecimal amount;
    private int duration;
  }
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class CreateResponse {
    private Long reportId;
    private BigDecimal totalDividend;
    private LocalDate createdAt;
    public CreateResponse(Report report) {
      this.reportId = report.getId();
      this.totalDividend = report.getTotalDividend();
      this.createdAt = report.getCreatedAt();

    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class SearchResponse {
    private Long reportId;
    private List<StockDto.StockGroupInfo> stockGroupInfoList;

    public void addStockGroupInfo(StockDto.StockGroupInfo stockGroupInfo) {
      stockGroupInfoList.add(stockGroupInfo);
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UserSearchResponse {
    private Long userId;
    private List<SearchResponse> reportList;

    public void addReportInfo(SearchResponse ReportInfo) {
      reportList.add(ReportInfo);
    }
  }

}