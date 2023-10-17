package com.shinhan.bullsandbears.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum CustomExceptionList {

	RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E001", "잘못된 요청입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E002", "서버 오류입니다."),
	NO_SUCH_DATA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E003", "데이터가 없습니다."),

	ACCESS_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "E101", "엑세스 토큰 오류입니다."),
	REFRESH_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "E102", " 리프레시 토큰 오류입니다."),

	NO_SUCH_USER_ERROR(HttpStatus.NOT_FOUND, "E201", "존재하지 않는 유저입니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	CustomExceptionList(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
