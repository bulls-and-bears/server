package com.shinhan.bullsandbears.domain.report;

import com.shinhan.bullsandbears.domain.stock.Stock;
import com.shinhan.bullsandbears.domain.stock.StockRepository;
import com.shinhan.bullsandbears.web.DTO.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportUseCase {

    private final StockRepository stockRepository;
    private final StockReportRelationRepository stockReportRelationRepository;

    @Transactional
    public ReportDto.CreateResponse createReport(ReportDto.CreateRequest request) {

        Duration duration = request.getDuration();
        BigDecimal amount = request.getAmount();

        List<Stock> stocks = stockRepository.findAll();
        List<Stock> calculatedStocks = calculateLogic(stocks, duration.getDays());

        List<Map<Stock, Integer>> optimalStocks = findOptimalStockCombination(calculatedStocks, amount);

        Map<Stock, Integer> comb = optimalStocks.get(0);
        BigDecimal totalDividend = BigDecimal.ZERO;

        for (Map.Entry<Stock, Integer> entry : comb.entrySet()) {
            Stock stock = entry.getKey();
            int count = entry.getValue();
            BigDecimal decimalCount = new BigDecimal(count);
            BigDecimal dividend = stock.getDividendAmount().multiply(decimalCount);
            totalDividend = totalDividend.add(dividend);
        }
        System.out.println(totalDividend);

        Report report = Report.builder()
                .duration(duration)
                .amount(amount)
                .createdAt(LocalDate.now())
                .totalDividend(totalDividend)
                .build();

        for (int groupNum = 0; groupNum < optimalStocks.size(); groupNum++) {
            Map<Stock, Integer> combination = optimalStocks.get(groupNum);
            for (Map.Entry<Stock, Integer> entry : combination.entrySet()) {
                Stock stock = entry.getKey();
                int count = entry.getValue();
                if (count != 0) {
                    Integer stockGroup = Integer.valueOf(groupNum);
                    createStockReport(stock, report, count, stockGroup);
                }
            }
        }
        return new ReportDto.CreateResponse(report);
    }

    @Transactional
    public void createStockReport(Stock stock, Report report, Integer stockUnits, Integer stockGroup) {

        stockReportRelationRepository.save(
                StockReportRelation.builder()
                        .stock(stock)
                        .report(report)
                        .stockUnits(stockUnits)
                        .stockGroup(stockGroup)
                        .build()
        );
    }

    public List<Stock> calculateLogic(List<Stock> stocks, Integer duration) {

        List<Stock> filteredByDividendRecordDateStocks = filterByDividendRecordDate(stocks, LocalDate.now());
        List<Stock> filteredStocksByPurchaseDate = filterStocksByPurchaseDate(filteredByDividendRecordDateStocks, LocalDate.now());
        List<Stock> sortedStocks = sortedByDividendPerShareRatio(filteredStocksByPurchaseDate);

        return sortedStocks;
    }

    public List<Stock> filterByDividendRecordDate(List<Stock> stocks, LocalDate currentDate) {

        return stocks.stream()
                .filter(stock -> stock.getDividendRecordDate().isAfter(currentDate))
                .collect(Collectors.toList());
    }

    public List<Stock> filterStocksByPurchaseDate(List<Stock> stocks, LocalDate purchaseDate) {
        return stocks.stream()
                .filter(stock -> purchaseDate.isBefore(stock.getDividendRecordDate().minusDays(1)))
                .collect(Collectors.toList());
    }

    public List<Stock> sortedByDividendPerShareRatio(List<Stock> stocks) {
        Comparator<Stock> dividendComparator = Comparator.comparing(Stock::getDividendPerShareRatio).reversed();

        return stocks.stream()
                .sorted(dividendComparator)
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Map<Stock, Integer>> findOptimalStockCombination(List<Stock> stocks, BigDecimal amount) {
        List<Map<Stock, Integer>> optimalCombinations = new ArrayList<>();
        Map<Stock, Integer> currentCombination = new HashMap<>();
        backtrack(optimalCombinations, currentCombination, stocks, amount, 0);

        return optimalCombinations;
    }

    private void backtrack(List<Map<Stock, Integer>> optimalCombinations,
                           Map<Stock, Integer> currentCombination,
                           List<Stock> stocks,
                           BigDecimal remainingAmount,
                           int currentIndex) {

        if (currentIndex == stocks.size()) {
            BigDecimal currentDividend = calculateDividend(currentCombination);
            BigDecimal maxDividend;
            if (!optimalCombinations.isEmpty()) {
                maxDividend = calculateDividend(optimalCombinations.get(0));
            } else {
                maxDividend = BigDecimal.ZERO;
            }
            if (currentDividend.compareTo(maxDividend) > 0) {
                optimalCombinations.clear();
                optimalCombinations.add(new HashMap<>(currentCombination));
            } else if (currentDividend.equals(maxDividend)) {
                optimalCombinations.add(new HashMap<>(currentCombination));
            }
            return;
        }

        Stock stock = stocks.get(currentIndex);
        int maxQuantity = remainingAmount.divide(stock.getPrice(), BigDecimal.ROUND_DOWN).intValue();
        for (int quantity = 0; quantity <= maxQuantity; quantity++) {
            BigDecimal cost = stock.getPrice().multiply(BigDecimal.valueOf(quantity));
            if (cost.compareTo(remainingAmount) <= 0) {
                currentCombination.put(stock, quantity);
                BigDecimal remaining = remainingAmount.subtract(cost);
                backtrack(optimalCombinations, currentCombination, stocks, remaining, currentIndex + 1);
                currentCombination.remove(stock);
            }
        }
    }

    private BigDecimal calculateDividend(Map<Stock, Integer> combination) {
        return combination.entrySet().stream()
                .map(entry -> entry.getKey().getDividendAmount().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
