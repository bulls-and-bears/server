package com.shinhan.bullsandbears.domain.real.stock;

import java.util.List;

public class SimulationStock {
    Stock stock;
    List<Integer> priceChangeTimings;
    List<Integer> priceChangeRate;

    // TODO(재연) require(priceChangeTimings.size == priceChangeRate.size)
}
