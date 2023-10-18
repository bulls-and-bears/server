package com.shinhan.bullsandbears.domain.stock;

import org.springframework.stereotype.Service;


import com.shinhan.bullsandbears.domain.report.Duration;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockUseCase {

    final StockRepository stockRepository;

    public StockUseCase(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> findTop5StocksByDividendRatio(String duration) {
        return findSortedStocksByDividendRatio(LocalDate.of(2023, 4, 12), Duration.valueOf(duration));
        // TODO : 실제 서비스 시 currentTime으로 변경
    }

    private List<Stock> findSortedStocksByDividendRatio(LocalDate referenceDate, Duration duration) {
        return stockRepository.findAll().stream()
                .filter(stock -> isValidByDividendRecordDate(stock, referenceDate))
                .filter(stock -> isOverMinimumHoldingPeriod(stock, duration))
                .sorted(Comparator.comparing(Stock::getDividendPerShareRatio).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    private boolean isValidByDividendRecordDate(Stock stock, LocalDate currentDate) {
        return stock.getDividendRecordDate().isAfter(currentDate);
    }

    private boolean isOverMinimumHoldingPeriod(Stock stock, Duration duration) {
        return (java.time.Period.between(stock.getDividendRecordDate(), stock.getDividendPaymentDate()).getDays() + 2) < duration.getDays();
    }


}
