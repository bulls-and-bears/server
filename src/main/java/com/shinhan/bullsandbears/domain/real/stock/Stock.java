package com.shinhan.bullsandbears.domain.real.stock;

import com.shinhan.bullsandbears.domain.real.report.Report;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    private String stockName;       // 종목명
    private BigDecimal dividendAmount;    // 주당 배당금
    private BigDecimal dividendYieldRate;      // 시가 배당률
    private String stockType;       // 주식 종류
    private Date dividendRecordDate;        // 배당 기준일
    private Date dividendPaymentDate;       // 배당금 지급일
    private Long marketPrice;       // 주식 가격
    private LocalDate savedAt;      // 데이터 저장 일자
    private LocalDate updatedAt;        // 데이터 수정 일자

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;
}
