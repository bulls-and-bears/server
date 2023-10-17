package com.shinhan.bullsandbears.web;

import com.shinhan.bullsandbears.domain.report.ReportUseCase;
import com.shinhan.bullsandbears.web.DTO.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private final ReportUseCase reportUseCase;
    private final SimulationReportUseCase simulationReportUseCase;

    @PostMapping("/")
    public ResponseEntity<ReportDto.CreateResponse> createReport(@RequestBody ReportDto.CreateRequest reportDto) {
        return ResponseEntity.status(HttpStatus.OK).body(reportUseCase.createReport(reportDto));
    }

    public ReportController(ReportUseCase reportUseCase, SimulationReportUseCase simulationReportUseCase) {
        this.reportUseCase = reportUseCase;
        this.simulationReportUseCase = simulationReportUseCase;
    }


    @GetMapping("/report")
    public Report get(@RequestBody Long money, @RequestBody String duration) {
        return reportUseCase.get(money, duration);
        // TODO(영현) 배당금 총액, 종목 5개 각각 몇 주인지 불러오는 get api
    }


    @GetMapping("/simulate")
    public SimulationReport get(@RequestBody Long money, @RequestBody String duration, @RequestBody Long count) {
        return simulationReportUseCase.get(money, duration, count);
    }
}
