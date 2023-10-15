package com.shinhan.bullsandbears.domain.real.report;

import com.shinhan.bullsandbears.domain.real.stock.SimulationStock;
import com.shinhan.bullsandbears.domain.real.stock.Stock;
import com.shinhan.bullsandbears.domain.real.stock.StockUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimulationReportUseCase {

    private final StockUseCase stockUseCase;

    public SimulationReportUseCase(StockUseCase stockUseCase) {
        this.stockUseCase = stockUseCase;
    }

    // TODO(재연) 시뮬레이션 개인화 저장하는 api 구현, SimulationReportRepository 구현

    public SimulationReport get(Long money, String duration, Long count) {
        // TODO(재연) count만큼 SimulationReport 만들어서 Return
        return simulate(money, Duration.valueOf(duration), count);
    }

    private SimulationReport simulate(Long money, Duration duration, Long count) {
        List<Stock> allStocks = stockUseCase.findAll(); // TODO(재연) 시뮬레이션 전 report의 주식들 불러오기
        List<SimulationStock> randomizedStocks =
                allStocks.stream()
                        .map(this::createSimulationStock)
                        .toList();
        // TODO(재연) count 횟수 만큼 주식 가격 시뮬레이션
        return simulationReport(randomizedStocks);
    }

    private SimulationReport simulationReport(List<SimulationStock> simulationStocks) {
        // TODO(재연) 랜덤화된 주식들 받아서 Simulation report 생성
        return new SimulationReport();
    }

    /**
     * 주식의 배당금과 시장가격을 랜덤화 합니다.
     * Change timings:
     * - 배당금: 주식의 역대 배당 이력을 바탕으로 결정
     * - 시장 가격: Daily?
     * Change rate: +1%, -4%, +2%, etc.
     *
     * @return 성공적으로 데이터베이스가 연결되면 OK를 반환합니다.
     */
    private SimulationStock createSimulationStock(Stock stock) {
        // TODO(재연) randomize 배당금, 시장가격
        // change timings : 배당금 -> 역대 배당 이력에 따라서 결정, 시장 가격 -> 매일?
        // change rate: +1%, -4%, +2%...
        return new SimulationStock();
    }
}
