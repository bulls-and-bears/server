package com.shinhan.bullsandbears.report;
import com.shinhan.bullsandbears.domain.Duration;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
public class ReportDto {
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class CreateRequest {
    private BigDecimal amount;
    private Duration duration;
  }
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class CreateResponse {
    private Long reportId;
    private LocalDate createdAt;
    public CreateResponse(Report report) {
      this.reportId = report.getId();
      this.createdAt = report.getCreatedAt();

    }
  }

}