package com.shinhan.bullsandbears.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ReportDto {

    private Long money;
    private String duration;
    private Map<String, Long> table;    // 주식 명 : 개수
    private Long totalDividend; // 총 배당금
    private Long totalValuation;    // 총 비용(포트폴리오의 가치)

}
