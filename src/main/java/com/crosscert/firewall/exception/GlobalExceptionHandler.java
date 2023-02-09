package com.crosscert.firewall.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler()
    public ErrorResponse handleException(CustomException e) {
        ErrorCode error = e.getErrorCode();
        return new ErrorResponse(error.getHttpStatus(),error.getResultType(), error.getCode(), error.getMessage());
    }
}
