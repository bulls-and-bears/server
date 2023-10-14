package com.shinhan.bullsandbears.report;

import com.shinhan.bullsandbears.domain.Duration;
import com.shinhan.bullsandbears.stock.StockMaster;
import com.shinhan.bullsandbears.domain.StockReportHistory;
import com.shinhan.bullsandbears.stock.StockRepository;
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
public class ReportServiceImpl implements ReportService {

  private final StockRepository stockRepository;
  private final StockReportHistoryRepository stockReportHistoryRepository;

  @Override
  @Transactional
  public ReportDto.CreateResponse createReport(ReportDto.CreateRequest request) {

    Duration duration = request.getDuration();
    BigDecimal amount = request.getAmount();

    List<StockMaster> stocks = stockRepository.findAll();
    List<StockMaster> calculatedStocks = calculateLogic(stocks, duration.getDays());
    List<StockMaster> optimalStocks = findOptimalStockCombination(calculatedStocks, amount);

    Report report = Report.builder()
            .duration(duration)
            .amount(amount)
            .createdAt(LocalDate.now())
            .build();

    optimalStocks.forEach(stock -> createStockReport(stock, report));

    return new ReportDto.CreateResponse(report);
  }

  @Transactional
  public void createStockReport(StockMaster stock, Report report) {

    stockReportHistoryRepository.save(
            StockReportHistory.builder()
                    .stockMaster(stock)
                    .report(report)
                    .build()
    );
  }

  public List<StockMaster> calculateLogic(List<StockMaster> stocks, Integer duration) {

    List<StockMaster> filteredByDividendRecordDateStocks = filterByDividendRecordDate(stocks, LocalDate.now());
    List<StockMaster> filteredStocksByPurchaseDate = filterStocksByPurchaseDate(filteredByDividendRecordDateStocks,LocalDate.now());

    List<StockMaster> sortedStocks = sortedByDividendPerShareRatio(filteredStocksByPurchaseDate);

    return sortedStocks;
  }

  public List<StockMaster> filterByDividendRecordDate(List<StockMaster> stocks, LocalDate currentDate){

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

  public List<StockMaster> findOptimalStockCombination(List<StockMaster> stocks, BigDecimal amount) {

    int n = stocks.size();
    List<List<BigDecimal>> dp = new ArrayList<>(n + 1);


    for (int i = 0; i <= n; i++) {
      List<BigDecimal> row = new ArrayList<>(amount.intValue() + 1);
      for (int j = 0; j <= amount.intValue(); j++) {
        row.add(BigDecimal.ZERO);
      }
      dp.add(row);
    }

    for (int i = 1; i <= n; i++) {
      StockMaster stock = stocks.get(i - 1);
      BigDecimal price = stock.getPrice();
      BigDecimal dividend = stock.getDividendAmount();

      for (int j = 0; j <= amount.intValue(); j++) {
        BigDecimal maxWithoutSelect = dp.get(i - 1).get(j);

        dp.get(i).set(j, maxWithoutSelect);

        for (int k = 1; k * price.intValue() <= j; k++) {
          BigDecimal selectMultipleTimes = dp.get(i - 1).get(j - k * price.intValue())
                  .add(BigDecimal.valueOf(k).multiply(dividend));
          
          if (selectMultipleTimes.compareTo(dp.get(i).get(j)) > 0) {
            dp.get(i).set(j, selectMultipleTimes);
          }
        }
      }

    }

    int j = amount.intValue();
    List<StockMaster> selectedStocks = new ArrayList<>();
    for (int i = n; i > 0 && j > 0; i--) {
      if (!dp.get(i).get(j).equals(dp.get(i - 1).get(j))) {
        selectedStocks.add(stocks.get(i - 1));
        j -= stocks.get(i - 1).getPrice().intValue();
      }
    }

    return selectedStocks;
  }
}
