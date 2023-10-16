package com.shinhan.bullsandbears.domain.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Jwt {

	private final String accessToken;
	private final String refreshToken;
}
