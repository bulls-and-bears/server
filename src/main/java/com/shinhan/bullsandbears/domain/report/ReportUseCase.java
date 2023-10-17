package com.shinhan.bullsandbears.domain.report;

import com.shinhan.bullsandbears.domain.stock.StockMaster;
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

        List<StockMaster> stocks = stockRepository.findAll();
        List<StockMaster> calculatedStocks = calculateLogic(stocks, duration.getDays());

        List<Map<StockMaster, Integer>> optimalStocks = findOptimalStockCombination(calculatedStocks, amount);

        Map<StockMaster, Integer> comb = optimalStocks.get(0);
        BigDecimal totalDividend = BigDecimal.ZERO;

        for (Map.Entry<StockMaster, Integer> entry : comb.entrySet()) {
            StockMaster stock = entry.getKey();
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
            Map<StockMaster, Integer> combination = optimalStocks.get(groupNum);
            for (Map.Entry<StockMaster, Integer> entry : combination.entrySet()) {
                StockMaster stock = entry.getKey();
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
    public void createStockReport(StockMaster stock, Report report, Integer stockUnits, Integer stockGroup) {

        stockReportRelationRepository.save(
                StockReportRelation.builder()
                        .stockMaster(stock)
                        .report(report)
                        .stockUnits(stockUnits)
                        .stockGroup(stockGroup)
                        .build()
        );
    }

    public List<StockMaster> calculateLogic(List<StockMaster> stocks, Integer duration) {

        List<StockMaster> filteredByDividendRecordDateStocks = filterByDividendRecordDate(stocks, LocalDate.now());
        List<StockMaster> filteredStocksByPurchaseDate = filterStocksByPurchaseDate(filteredByDividendRecordDateStocks, LocalDate.now());
        List<StockMaster> sortedStocks = sortedByDividendPerShareRatio(filteredStocksByPurchaseDate);

        return sortedStocks;
    }

    public List<StockMaster> filterByDividendRecordDate(List<StockMaster> stocks, LocalDate currentDate) {

        return stocks.stream()
                .filter(stock -> stock.getDividendRecordDate().isAfter(currentDate))
                .collect(Collectors.toList());
    }

    public List<StockMaster> filterStocksByPurchaseDate(List<StockMaster> stocks, LocalDate purchaseDate) {
        return stocks.stream()
                .filter(stock -> purchaseDate.isBefore(stock.getDividendRecordDate().minusDays(1)))
                .collect(Collectors.toList());
    }

    public List<StockMaster> sortedByDividendPerShareRatio(List<StockMaster> stocks) {
        Comparator<StockMaster> dividendComparator = Comparator.comparing(StockMaster::getDividendPerShareRatio).reversed();

        return stocks.stream()
                .sorted(dividendComparator)
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Map<StockMaster, Integer>> findOptimalStockCombination(List<StockMaster> stocks, BigDecimal amount) {
        List<Map<StockMaster, Integer>> optimalCombinations = new ArrayList<>();
        Map<StockMaster, Integer> currentCombination = new HashMap<>();
        backtrack(optimalCombinations, currentCombination, stocks, amount, 0);

        return optimalCombinations;
    }

    private void backtrack(List<Map<StockMaster, Integer>> optimalCombinations,
                           Map<StockMaster, Integer> currentCombination,
                           List<StockMaster> stocks,
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

        StockMaster stock = stocks.get(currentIndex);
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

    private BigDecimal calculateDividend(Map<StockMaster, Integer> combination) {
        return combination.entrySet().stream()
                .map(entry -> entry.getKey().getDividendAmount().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
