package com.shinhan.bullsandbears.domain.real.stock;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockUseCase {

    final StockRepository stockRepository;

    public StockUseCase(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    // TODO(영현) 노션에 설명했던 주식 리스트 가져오는 쿼리를 구현한 함수 구현
}
