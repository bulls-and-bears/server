package com.shinhan.bullsandbears.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyRequestDto {

    private Long amount;
    private String duration;
}
