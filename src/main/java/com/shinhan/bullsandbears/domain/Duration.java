package com.shinhan.bullsandbears.domain;

import lombok.Getter;

@Getter
public enum Duration {
  TWO_MONTHS("2개월", 60),
  SIXTH_MONTHS("6개월", 180),
  ONE_YEAR("1년", 365),
  TWO_YEARS("2년", 730),
  THREE_YEARS("3년", 1095);

  private final String label;
  private final int days;
  
  Duration(String label, int days) {
    this.label = label;
    this.days = days;
  }

}
