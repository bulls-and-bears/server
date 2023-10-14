package com.shinhan.bullsandbears.stock;

import com.shinhan.bullsandbears.domain.StockReportHistory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockMaster {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "stock_id")
  private Long id;

  private String stockName;        // 종목명
  private String stockMarket;      // 시장명
  private BigDecimal price;        // 주식 가격
  private BigDecimal dividendAmount;      // 주당 배당금
  private BigDecimal DividendPerShareRatio;   // 시가 배당률
  private String stockType;        // 주식 종류
  private LocalDate dividendRecordDate;       // 배당금 기준일
  private LocalDate dividendPaymentDate;       // 배당금 지급일
  private BigDecimal faceValue;    // 액면가
  private LocalDate savedAt;       // 데이터 저장 일자
  private LocalDate updatedAt;     // 데이터 수정 일자

  @OneToMany(mappedBy = "stockMaster", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StockReportHistory> stockReportHistoryList = new ArrayList<>();

}
