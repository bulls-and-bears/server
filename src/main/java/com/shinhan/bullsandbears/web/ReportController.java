package com.shinhan.bullsandbears.web;

import com.shinhan.bullsandbears.domain.report.ReportUseCase;
import com.shinhan.bullsandbears.web.DTO.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController {

    private final ReportUseCase reportUseCase;

    @PostMapping("/")
    public ResponseEntity<ReportDto.CreateResponse> createReport(@RequestBody ReportDto.CreateRequest reportDto) {
        return ResponseEntity.status(HttpStatus.OK).body(reportUseCase.createReport(reportDto));
    }
}
