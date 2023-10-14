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

    Report report = Report.builder()
            .duration(duration)
            .amount(amount)
            .createdAt(LocalDate.now())
            .build();

    calculatedStocks.forEach(stock -> createStockReport(stock, report));

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
}
