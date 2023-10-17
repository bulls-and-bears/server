package com.shinhan.bullsandbears.domain.stock;

import org.springframework.stereotype.Service;

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

    public List<Stock> findTop5StocksByDividendRatio() {
        return findSortedStocksByDividendRatio(LocalDate.of(2022, 4, 12));
        // TODO : 실제 서비스 시 currentTime으로 변경
    }

    private List<Stock> findSortedStocksByDividendRatio(LocalDate referenceDate) {
        return stockRepository.findAll().stream()
                .filter(stock -> isValidByDividendRecordDate(stock, referenceDate))
                .filter(stock -> isValidByPurchaseDate(stock, referenceDate))
                .sorted(Comparator.comparing(Stock::getDividendPerShareRatio).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    private boolean isValidByDividendRecordDate(Stock stock, LocalDate currentDate) {
        return stock.getDividendRecordDate().isAfter(currentDate);
    }

    private boolean isValidByPurchaseDate(Stock stock, LocalDate purchaseDate) {
        return purchaseDate.isBefore(stock.getDividendRecordDate().minusDays(1));
    }

}
