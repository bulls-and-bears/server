package com.shinhan.bullsandbears.web;

import com.shinhan.bullsandbears.domain.report.ReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ReportController {

    private final ReportUseCase reportUseCase;

    @GetMapping("/report")
    public ReportDto get(@RequestParam Long money, @RequestParam String duration) {
        return reportUseCase.get(money, duration);
    }

}
