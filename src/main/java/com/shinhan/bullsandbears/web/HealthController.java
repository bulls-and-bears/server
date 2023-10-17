package com.shinhan.bullsandbears.web;

import com.shinhan.bullsandbears.domain.report.Duration;
import com.shinhan.bullsandbears.domain.report.Report;
import com.shinhan.bullsandbears.domain.report.ReportRepository;
import com.shinhan.bullsandbears.domain.stock.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final ReportRepository reportRepository;

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
}
