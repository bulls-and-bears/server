package com.shinhan.bullsandbears.domain;

import javax.persistence.*;

import com.shinhan.bullsandbears.report.Report;
import com.shinhan.bullsandbears.stock.StockMaster;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockReportHistory {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="stock_report_history_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="stock_id")
  private StockMaster stockMaster;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name="report_id")
  private Report report;

  @Builder
  public StockReportHistory(StockMaster stockMaster, Report report){
    this.stockMaster = stockMaster;
    this.report = report;
  }

}
