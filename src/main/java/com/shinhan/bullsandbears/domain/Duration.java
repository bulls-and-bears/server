package com.shinhan.bullsandbears.domain;

import lombok.Getter;

@Getter
public enum Duration {
  TWO_MONTHS("2개월", 2),
  FOUR_MONTHS("4개월", 4),
  SIX_MONTHS("6개월", 6),
  EIGHT_MONTHS("8개월", 8),
  TEN_MONTHS("10개월", 10);

  private final String label;
  private final int days;

  Duration(String label, int days) {
    this.label = label;
    this.days = days;
  }

}
