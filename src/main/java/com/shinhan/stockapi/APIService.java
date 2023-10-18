package com.shinhan.stockapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class APIService {
  private final WebClient webClient;
  @Autowired
  public APIService(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("https://apis.data.go.kr").build();
  }
  public StockResponse fetchStockData(String beginBasDt, String endBasDt, String itmsNm) {
    APIResponse apiResponse = webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/1160100/service/GetStockSecuritiesInfoService")
                    .queryParam("beginBasDt", beginBasDt)
                    .queryParam("endBasDt", endBasDt)
                    .queryParam("itmsNm", itmsNm)
                    .queryParam("serviceKey", "YAHejXbd2HxbGwKQtxuWlRLdiU4FrJ4J5AYbzLjFbmb6/SY+EiiprL8D72glg7Exb2Vg3/kKPFMUVucAhpigLA==")  // 여기에 인증키를 추가
                    .build())
            .retrieve()
            .bodyToMono(APIResponse.class)
            .block();

    APIResponse.Item item = apiResponse.getBody().getItems().getItem();
    return new StockResponse(item.getItmsNm(), item.getFltRt(), item.getClpr());
  }
}