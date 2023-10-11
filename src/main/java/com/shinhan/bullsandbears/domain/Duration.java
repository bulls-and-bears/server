package com.shinhan.bullsandbears.domain;

import lombok.Getter;

@Getter
public enum Duration {

  SIXTH_MONTHS("6개월"),
  ONE_YEAR("1년"),
  TWO_YEARS("2년"),
  THREE_YEARS("3년");

  private final String label;

  Duration(String label) {
    this.label = label;
  }

}
