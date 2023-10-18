package com.shinhan.bullsandbears.domain.simulation;

import com.shinhan.bullsandbears.web.ReportDto;
import com.shinhan.bullsandbears.web.SimulationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SimulationReportUseCase {

    private final CacheManager cacheManager;

    public List<SimulationDto> get(Long money, String duration, Long expectedYear) {
        return simulation(money, duration, expectedYear);
    }

    private List<SimulationDto> simulation(Long money, String duration, Long expectedYear) {

        List<SimulationDto> simulationDtoList = new ArrayList<>();
        ReportDto cached = getCachedReportDto(money, duration);

        assert cached != null;
        Long totalDividend = cached.getTotalDividend();
        Long totalValuation = cached.getTotalValuation();

        LocalDate date = LocalDate.now();
        Random random = new Random();
        for (long i = 0; i < expectedYear * 4; i++) {
            double rate = (random.nextDouble() * 20 - 10) / 100;

            date = date.plusMonths(3);
            totalDividend = (long) (totalDividend * (1 + rate));
            totalValuation = (long) (totalDividend * (1 + rate));
            simulationDtoList.add(new SimulationDto(date, totalDividend, totalValuation));
        }

        return simulationDtoList;
    }

    private ReportDto getCachedReportDto(Long money, String duration) {
        Cache reportsCache = cacheManager.getCache("reports");
        if (reportsCache != null) {
            Cache.ValueWrapper cachedValue = reportsCache.get(money + "-" + duration);
            if (cachedValue != null) {
                return (ReportDto) cachedValue.get();
            }
        }
        return null;
    }

}

