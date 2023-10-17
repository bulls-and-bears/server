package com.shinhan.bullsandbears.domain.stock;

import java.util.List;

public class SimultaionStock {
    private Stock stock;
    private List<Integer> priceChangeTimings;
    private List<Integer> priceChangeRate;

    // TODO(재연) require(priceChangeTimings.size == priceChangeRate.size)
}
