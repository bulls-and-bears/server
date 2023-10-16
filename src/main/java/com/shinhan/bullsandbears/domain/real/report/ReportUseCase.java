package com.shinhan.bullsandbears.domain.real.report;

import com.shinhan.bullsandbears.domain.real.stock.Stock;
import com.shinhan.bullsandbears.domain.real.stock.StockUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportUseCase {

    private final StockUseCase stockUseCase;

    public ReportUseCase(StockUseCase stockUseCase) {
        this.stockUseCase = stockUseCase;
    }

    public Report get(Long money, String duration) {
        List<Stock> selectedStocks = stockUseCase.findAll(); // TODO(영현) JPA 쿼리로 주식 가져오기. 임시로 모든 주식 가져오는 쿼리로 작성
        return report(money, Duration.valueOf(duration), selectedStocks);
    }

    private Report report(Long money, Duration duration, List<Stock> stocks) {
        // TODO(영현) 파라매터 값 유효성 검사 함수 구현해서 호출(시간 남으면 합시다.)
        // TODO(영현) 배당 수익률 상위 5개의 주식에서 받을 수 있는 최대 배당금을 구해서 report 객체로 반환 (knapsack problem)
        return new Report();
    }

}
