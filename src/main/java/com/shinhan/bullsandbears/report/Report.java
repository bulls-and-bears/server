package com.shinhan.bullsandbears.report;

import com.shinhan.bullsandbears.domain.Duration;
import com.shinhan.bullsandbears.domain.StockReportHistory;
import com.shinhan.bullsandbears.domain.UserReportHistory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_id")
  private Long id;

  @Enumerated(EnumType.STRING)
  private Duration duration;

  private BigDecimal amount;
  private LocalDate createdAt;

  private BigDecimal totalDividend;

  @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StockReportHistory> stockReportHistoryList = new ArrayList<>();

  @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserReportHistory> userReportHistoryList = new ArrayList<>();

  @Builder
  public Report (Duration duration, BigDecimal amount, LocalDate createdAt, BigDecimal totalDividend){
    this.duration = duration;
    this.amount = amount;
    this.createdAt = createdAt;
    this.totalDividend = totalDividend;
  }

  public void addTotalDividend(BigDecimal totalDividend) {
    this. totalDividend = totalDividend.add(totalDividend);
  }

}
