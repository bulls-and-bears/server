package com.shinhan.bullsandbears.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum CustomExceptionList {

	RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E001", "잘못된 요청입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E002", "서버 오류입니다."),
	ACCESS_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "E003", "엑세스 토큰 오류입니다."),
	NO_SUCH_ELEMENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E004", "데이터가 없습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	CustomExceptionList(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
