package com.shinhan.bullsandbears.domain.report;

import com.shinhan.bullsandbears.domain.stock.Stock;
import com.shinhan.bullsandbears.domain.stock.StockUseCase;
import com.shinhan.bullsandbears.web.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportUseCase {

    private final StockUseCase stockUseCase;

    // get 요청 보내면 reportDto를 반환합니다.
    public ReportDto get(Long money, String duration) {
        return reportDto(money, stockUseCase.findTop5StocksByDividendRatio(duration));
    }

    private ReportDto reportDto(Long money, List<Stock> topStocks) {
        int n = topStocks.size();
        Long[][] dp = new Long[n + 1][(int) (long) money + 1];

        // DP 테이블 초기화
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= money; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0L;
                } else {
                    Stock stock = topStocks.get(i - 1);
                    if (stock.getPrice().longValue() <= j) {
                        dp[i][j] = Math.max(dp[i - 1][j],
                                dp[i][j - stock.getPrice().intValue()] + stock.getDividendAmount().longValue());
                    } else {
                        dp[i][j] = dp[i - 1][j];
                    }
                }
            }
        }

        // 최대 배당금 및 총 비용 계산
        Long maxDividend = dp[n][(int) (long) money];
        Long totalCost = 0L;
        Map<String, Long> stockTable = new HashMap<>();

        int w = (int) (long) money;
        for (int i = n; i > 0 && maxDividend > 0; i--) {
            while (maxDividend != dp[i - 1][w] && w >= topStocks.get(i - 1).getPrice().longValue()) {
                stockTable.put(topStocks.get(i - 1).getStockName(), stockTable.getOrDefault(topStocks.get(i - 1).getStockName(), 0L) + 1L);
                maxDividend -= topStocks.get(i - 1).getDividendAmount().longValue();
                totalCost += topStocks.get(i - 1).getPrice().longValue();
                w -= topStocks.get(i - 1).getPrice().longValue();
            }
        }

        // 결과 DTO 반환
        ReportDto report = new ReportDto();
        report.setTable(stockTable);
        report.setTotalDividend(dp[n][(int) (long) money]);
        report.setTotalValuation(totalCost);

        return report;
    }


}
