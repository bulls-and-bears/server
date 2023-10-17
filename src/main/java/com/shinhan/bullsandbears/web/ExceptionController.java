package com.shinhan.bullsandbears.web;

import com.shinhan.bullsandbears.common.exception.CustomException;
import com.shinhan.bullsandbears.common.exception.CustomExceptionList;
import com.shinhan.bullsandbears.common.exception.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> customExceptionHandler(CustomException e) {
        return toEntity(e.getException());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> runtimeExceptionHandler(RuntimeException e) {
        log.warn(e.getMessage());
        return toEntity(CustomExceptionList.RUNTIME_EXCEPTION);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception e) {
        log.warn(e.getMessage());
        return toEntity(CustomExceptionList.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionResponse> toEntity(CustomExceptionList exceptionType) {
        return ResponseEntity
                .status(exceptionType.getStatus())
                .body(ExceptionResponse.builder()
                        .code(exceptionType.getCode())
                        .message(exceptionType.getMessage())
                        .build());
    }
}
