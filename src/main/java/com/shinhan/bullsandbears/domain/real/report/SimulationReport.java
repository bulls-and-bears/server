package com.shinhan.bullsandbears.domain.real.report;

import com.shinhan.bullsandbears.domain.real.stock.Stock;

import java.util.List;

public class SimulationReport {
    // TODO(재연) JPA 상속관계 매핑 구현?
    List<Stock> realStocks;
    List<Report> reports;
}
