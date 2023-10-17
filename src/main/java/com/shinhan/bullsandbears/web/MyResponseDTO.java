package com.shinhan.bullsandbears.web;

import com.shinhan.bullsandbears.domain.report.Duration;
import com.shinhan.bullsandbears.domain.stock.Stock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MyResponseDTO {

    private Duration duration;
    private Long amount;
    private Long totalDividend;
    private LocalDate createdAt;
    private List<Stock> stocks;

}
