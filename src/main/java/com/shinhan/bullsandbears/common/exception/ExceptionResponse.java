package com.shinhan.bullsandbears.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ExceptionResponse {

	private final String code;
	private final String message;
}