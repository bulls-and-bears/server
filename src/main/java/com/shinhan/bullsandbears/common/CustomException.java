package com.shinhan.bullsandbears.common;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final CustomExceptionList exception;

    public CustomException(CustomExceptionList exception) {
        super(exception.getMessage());
        this.exception = exception;
    }
}
