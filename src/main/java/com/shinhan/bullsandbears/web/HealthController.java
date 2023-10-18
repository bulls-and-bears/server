package com.shinhan.bullsandbears.web;

import com.shinhan.bullsandbears.domain.report.Duration;
import com.shinhan.bullsandbears.domain.report.Report;
import com.shinhan.bullsandbears.domain.report.ReportRepository;
import com.shinhan.bullsandbears.domain.stock.Stock;
import com.shinhan.bullsandbears.domain.stock.StockUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final ReportRepository reportRepository;
    private final StockUseCase stockUseCase;

    /**
     * DB 커넥션 확인을 위한 엔드포인트입니다.
     * 테스트 레포트를 생성하고 데이터베이스에 저장하여 데이터베이스 연결이 올바르게 작동하는지 확인합니다.
     *
     * @return 성공적으로 데이터베이스가 연결되면 OK를 반환합니다.
     */
    @GetMapping("/health")
    public String health() {
        Report testReport = new Report();
        testReport.setTotalDividend(999L);
        testReport.setAmount(999L);
        testReport.setDuration(Duration.ONE_YEAR);
        reportRepository.save(testReport);
        return "ok";
    }

    @GetMapping("/stocks")
    public String stock(@RequestParam String duration) {
        StringBuilder sb = new StringBuilder();
        List<Stock> top5 = stockUseCase.findTop5StocksByDividendRatio(duration);
        for (int i = 0; i < top5.size(); i++) {
            sb.append(top5.get(i).getStockName());
            sb.append("Top 5 잘 뽑혔는지 확인");
            sb.append(i);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
