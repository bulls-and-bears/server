package com.shinhan.bullsandbears.domain.report;

import lombok.Getter;

@Getter
public enum Duration {

    TWO_MONTHS("2개월", 60),
    SIXTH_MONTHS("6개월", 180);

    private final String label;
    private final int days;

    Duration(String label, int days) {
        this.label = label;
        this.days = days;
    }

}
