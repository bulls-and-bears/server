package com.shinhan.bullsandbears.domain.real.report;

import com.shinhan.bullsandbears.domain.real.stock.Stock;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "total_dividend")
    private Long totalDividend;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "report")
    private List<Stock> stocks;
}
