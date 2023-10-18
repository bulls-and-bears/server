package com.shinhan.stockapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {
  private final APIService apiService;

  @Autowired
  public APIController(APIService apiService) {
    this.apiService = apiService;
  }

  @GetMapping("/stockData")
  public StockResponse getStockData(@RequestParam String beginBasDt,
                                    @RequestParam String endBasDt,
                                    @RequestParam String itmsNm) {
    return apiService.fetchStockData(beginBasDt, endBasDt, itmsNm);
  }
}
