package com.shinhan.bullsandbears.domain.report;

import javax.persistence.*;

import com.shinhan.bullsandbears.domain.report.Report;
import com.shinhan.bullsandbears.domain.stock.StockMaster;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockReportRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_report_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private StockMaster stockMaster;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "report_id")
    private Report report;

    private Integer stockGroup;   // 주식 리스트 그룹
    private Integer stockUnits;   // 하나의 주식당 구매한 개수

    @Builder
    public StockReportRelation(StockMaster stockMaster, Report report, Integer stockUnits, Integer stockGroup) {
        this.stockMaster = stockMaster;
        this.report = report;
        this.stockUnits = stockUnits;
        this.stockGroup = stockGroup;
    }

}
