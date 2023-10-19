package com.shinhan.bullsandbears.stock;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public class StockDto {
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class StockInfo {
    private String stockName;
    private Integer amount;
    private BigDecimal price;
    private BigDecimal dividend;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class StockGroupInfo {
    private Integer groupId;
    private List<StockInfo> stockInfoList;

    public void addStock(StockInfo stockInfo) {
      stockInfoList.add(stockInfo);
    }
  }
}