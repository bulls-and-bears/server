package com.shinhan.bullsandbears.domain.report;

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

    private BigDecimal totalDividend;

    private LocalDate createdAt;


    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockReportRelation> stockReportRelationList = new ArrayList<>();

    @Builder
    public Report(Duration duration, BigDecimal amount, BigDecimal totalDividend, LocalDate createdAt) {
        this.duration = duration;
        this.amount = amount;
        this.totalDividend = totalDividend;
        this.createdAt = createdAt;
    }

    public void addTotalDividend(BigDecimal totalDividend) {
        this.totalDividend = totalDividend.add(totalDividend);
    }

}
