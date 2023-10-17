package com.shinhan.bullsandbears.domain.report;

import com.shinhan.bullsandbears.domain.relation.StockReportRelation;
import com.shinhan.bullsandbears.domain.stock.Stock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "duration")
    private Duration duration;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "total_dividend")
    private Long totalDividend;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "report")
    private List<StockReportRelation> stockReportRelations = new ArrayList<>();
}
