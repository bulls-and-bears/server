package com.shinhan.bullsandbears.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class SimulationDto {

    private LocalDate date;
    private Long dividend;
    private Long valuation;

    public SimulationDto(LocalDate date, Long totalDividend, Long totalValuation) {
        this.date = date;
        this.dividend = totalDividend;
        this.valuation = totalValuation;
    }
}
