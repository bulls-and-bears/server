package com.shinhan.bullsandbears.web;

import com.shinhan.bullsandbears.domain.real.report.Report;
import com.shinhan.bullsandbears.domain.real.report.ReportUseCase;
import com.shinhan.bullsandbears.domain.real.report.SimulationReport;
import com.shinhan.bullsandbears.domain.real.report.SimulationReportUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    private final ReportUseCase reportUseCase;
    private final SimulationReportUseCase simulationReportUseCase;

    public ReportController(ReportUseCase reportUseCase, SimulationReportUseCase simulationReportUseCase) {
        this.reportUseCase = reportUseCase;
        this.simulationReportUseCase = simulationReportUseCase;
    }


    @GetMapping("/report")
    public Report get(@RequestBody Long money, @RequestBody String duration) {
        return reportUseCase.get(money, duration);
    }


    @GetMapping("/simulate")
    public SimulationReport get(@RequestBody Long money, @RequestBody String duration, @RequestBody Long count) {
        return simulationReportUseCase.get(money, duration, count);
    }
}
