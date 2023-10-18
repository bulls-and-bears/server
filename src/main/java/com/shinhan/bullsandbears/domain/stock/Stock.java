package com.shinhan.bullsandbears.domain.stock;

import com.shinhan.bullsandbears.domain.relation.StockReportRelation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @Column(name = "stock_name")
    private String stockName;        // 종목명

    @Column(name = "market_price")
    private BigDecimal price;        // 주식 가격

    @Column(name = "dividend_amount")
    private BigDecimal dividendAmount;      // 주당 배당금

    @Column(name = "dividend_per_share_ratio")
    private BigDecimal dividendPerShareRatio;   // 시가 배당률

    @Column(name = "dividend_record_date")
    private LocalDate dividendRecordDate;       // 배당금 기준일

    @Column(name = "dividend_payment_date")
    private LocalDate dividendPaymentDate;       // 배당금 지급일

    @Column(name = "saved_at")
    private LocalDate savedAt;       // 데이터 저장 일자

    @Column(name = "updated_at")
    private LocalDate updatedAt;     // 데이터 수정 일자

    @OneToMany(mappedBy = "stock")
    private List<StockReportRelation> stockReportRelations = new ArrayList<>();

}
