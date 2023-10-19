package com.shinhan.bullsandbears.report;

import com.shinhan.bullsandbears.domain.Duration;
import com.shinhan.bullsandbears.domain.User;
import com.shinhan.bullsandbears.domain.UserReportHistory;
import com.shinhan.bullsandbears.repository.UserRepository;
import com.shinhan.bullsandbears.stock.StockDto;
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
  private final ReportRepository reportRepository;
  private final UserReportHistoryRepository userReportHistoryRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public ReportDto.CreateResponse createReport(ReportDto.CreateRequest request, String userName) {

    User user = findUserByName(userName);

    int durationValue = request.getDuration();

    Duration duration = findDurationByValue(durationValue);

    if (duration == null) {
      duration = Duration.TWO_MONTHS; 
    }
    BigDecimal amount = request.getAmount();

    List<StockMaster> stocks = stockRepository.findAll();
    List<StockMaster> calculatedStocks = calculateLogic(stocks, durationValue);

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
    createUserReport(user, report);
    return new ReportDto.CreateResponse(report);
  }

  private Duration findDurationByValue(int value) {
    for (Duration duration : Duration.values()) {
      if (duration.getDays() == value) {
        return duration;
      }
    }
    return null;
  }


  @Transactional
  public void createStockReport(StockMaster stock, Report report, Integer stockUnits, Integer stockGroup) {

    stockReportHistoryRepository.save(
            StockReportHistory.builder()
                    .stockMaster(stock)
                    .report(report)
                    .stockUnits(stockUnits)
                    .stockGroup(stockGroup)
                    .build()
    );
  }

  @Transactional
  public void createUserReport(User user, Report report) {
    userReportHistoryRepository.save(
            UserReportHistory.builder()
                    .user(user)
                    .report(report)
                    .build()
    );
  }


  public List<StockMaster> calculateLogic(List<StockMaster> stocks, int durationValue) {

    List<StockMaster> filteredByDividendRecordDateStocks = filterByDividendRecordDate(stocks, LocalDate.now());
    List<StockMaster> filteredStocksByPurchaseDate = filterStocksByPurchaseDate(filteredByDividendRecordDateStocks, LocalDate.now(), durationValue);

    List<StockMaster> sortedStocks = sortedByDividendPerShareRatio(filteredStocksByPurchaseDate);

    return sortedStocks;
  }

  public List<StockMaster> filterByDividendRecordDate(List<StockMaster> stocks, LocalDate currentDate) {

    return stocks.stream()
            .filter(stock -> stock.getDividendRecordDate().isAfter(currentDate))
            .collect(Collectors.toList());
  }

  // 현재 날짜 < 배당기준일 하루 전 < 현재날짜 + duration
  public List<StockMaster> filterStocksByPurchaseDate(List<StockMaster> stocks, LocalDate purchaseDate, int duration) {
    int durationDay = duration * 30;
    LocalDate endDate = purchaseDate.plusDays(durationDay);

    return stocks.stream()
            .filter(stock -> purchaseDate.isBefore(stock.getDividendRecordDate().minusDays(1))
                    && endDate.isAfter(stock.getDividendRecordDate().minusDays(1)))
            .collect(Collectors.toList());
  }


  public List<StockMaster> sortedByDividendPerShareRatio(List<StockMaster> stocks) {
    Comparator<StockMaster> dividendComparator = Comparator.comparing(StockMaster::getDividendPerShareRatio).reversed();

    return stocks.stream()
            .sorted(dividendComparator)
            .limit(6)
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

  @Override
  public ReportDto.SearchResponse searchReport(Long reportId) {

    Report report = findReportById(reportId);

    ReportDto.SearchResponse searchResponse = new ReportDto.SearchResponse(reportId, new ArrayList<>());

    List<StockReportHistory> stockReportHistoryList = report.getStockReportHistoryList();

    Map<Integer, List<StockReportHistory>> groupedStockMap = groupStockByGroupId(stockReportHistoryList);

    for (Map.Entry<Integer, List<StockReportHistory>> entry : groupedStockMap.entrySet()) {
      Integer groupId = entry.getKey();
      List<StockReportHistory> groupStockList = entry.getValue();

      StockDto.StockGroupInfo stockGroupInfo = new StockDto.StockGroupInfo(groupId, new ArrayList<>());

      for (StockReportHistory stock : groupStockList) {
        String stockName = stock.getStockMaster().getStockName();
        Integer stockUnits = stock.getStockUnits();
        BigDecimal stockPrice = stock.getStockMaster().getPrice();
        BigDecimal stockDividend = stock.getStockMaster().getDividendAmount();

        StockDto.StockInfo stockInfo = new StockDto.StockInfo(stockName, stockUnits, stockPrice, stockDividend);
        stockGroupInfo.addStock(stockInfo);
      }
      searchResponse.addStockGroupInfo(stockGroupInfo);
    }
    return searchResponse;
  }

  public Map<Integer, List<StockReportHistory>> groupStockByGroupId(List<StockReportHistory> stockReportHistoryList) {
    Map<Integer, List<StockReportHistory>> groupedStockMap = new HashMap<>();

    for (StockReportHistory stockReportHistory : stockReportHistoryList) {
      Integer groupId = stockReportHistory.getStockGroup();

      if (groupedStockMap.containsKey(groupId)) {
        groupedStockMap.get(groupId).add(stockReportHistory);
      } else {
        List<StockReportHistory> newGroup = new ArrayList<>();
        newGroup.add(stockReportHistory);
        groupedStockMap.put(groupId, newGroup);

      }
    }

    return groupedStockMap;
  }

  @Override
  public ReportDto.UserSearchResponse findReportByUser(String userName) {


    User user = findUserByName(userName);
    Long userId = user.getId();
    List<UserReportHistory> userReports = user.getUserReportHistoryList();

    ReportDto.UserSearchResponse searchResponseList = new ReportDto.UserSearchResponse(userId, new ArrayList<>());

    for (UserReportHistory userReport : userReports) {
      Long reportId = userReport.getReport().getId();
      Report report = findReportById(reportId);

      ReportDto.SearchResponse searchResponse = new ReportDto.SearchResponse(reportId, new ArrayList<>());
      List<StockReportHistory> stockReportHistoryList = report.getStockReportHistoryList();

      Map<Integer, List<StockReportHistory>> groupedStockMap = groupStockByGroupId(stockReportHistoryList);

      for (Map.Entry<Integer, List<StockReportHistory>> entry : groupedStockMap.entrySet()) {
        Integer groupId = entry.getKey();
        List<StockReportHistory> groupStockList = entry.getValue();

        StockDto.StockGroupInfo stockGroupInfo = new StockDto.StockGroupInfo(groupId, new ArrayList<>());

        for (StockReportHistory stock : groupStockList) {
          String stockName = stock.getStockMaster().getStockName();
          Integer stockUnits = stock.getStockUnits();
          BigDecimal stockPrice = stock.getStockMaster().getPrice();
          BigDecimal stockDividend = stock.getStockMaster().getDividendAmount();

          StockDto.StockInfo stockInfo = new StockDto.StockInfo(stockName, stockUnits, stockPrice, stockDividend);
          stockGroupInfo.addStock(stockInfo);
        }
        searchResponse.addStockGroupInfo(stockGroupInfo);
      }
      searchResponseList.addReportInfo(searchResponse);
    }
    return searchResponseList;
  }

  private User findUserByName(String name) {
    if (name != null) {
      return userRepository.findByName(name)
              .orElseThrow(() -> new NoSuchElementException("해당 이름 " + name + "와 일치하는 사용자가 존재하지 않습니다."));
    } else {
      throw new NoSuchElementException("해당 이름 " + name + "와 일치하는 사용자가 존재하지 않습니다.");

    }
  }

  private Report findReportById(Long reportId){
    Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new NoSuchElementException("해당 Id" + reportId + "와 일치하는 Report가 존재하지 않습니다."));
    return report;
  }
}



