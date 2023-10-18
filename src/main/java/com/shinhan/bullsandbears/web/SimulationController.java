package com.shinhan.bullsandbears.web;

import com.shinhan.bullsandbears.domain.simulation.SimulationReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class SimulationController {

    private final SimulationReportUseCase simulationReportUseCase;

    @GetMapping("/simulate")
    public List<SimulationDto> simulate(@RequestParam Long expectedYear, @RequestParam Long money, @RequestParam String duration) {
        System.out.println("simulate!");
        return simulationReportUseCase.get(money, duration, expectedYear);
    }

}
