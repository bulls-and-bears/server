package com.shinhan.bullsandbears.domain.real.report;

import lombok.Getter;

@Getter
public enum Duration {
    SIXTH_MONTHS("6개월"),
    TWO_MONTH("2개월"),
    ONE_YEARS("1년"),
    LONG_TERM("장기");

    private final String label;

    Duration(String label) {
        this.label = label;
    }
}
