package com.shinhan.bullsandbears.domain.report;

import com.shinhan.bullsandbears.domain.stock.Stock;
import com.shinhan.bullsandbears.domain.stock.StockUseCase;
import com.shinhan.bullsandbears.web.MyRequestDto;
import com.shinhan.bullsandbears.web.MyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportUseCase {

    private final StockUseCase stockUseCase;

    // Post
    public Report get(Long money, String duration) {

        Report report = report(
                money,
                Duration.valueOf(duration),
                stockUseCase.findTop5StocksByDividendRatio()
        );

        System.out.println("뭔데이거");
        return report;
    }

    private Report report(Long money, Duration duration, List<Stock> stocks) {
        Report temp = new Report();
        temp.setTotalDividend(80000L);
        return temp;
    }


}
