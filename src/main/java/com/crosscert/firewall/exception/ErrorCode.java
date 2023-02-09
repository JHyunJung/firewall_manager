package com.crosscert.firewall.exception;

import com.crosscert.firewall.entity.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //예시 코드입니다.
    NOT_EXIST_IP(HttpStatus.NOT_FOUND, ResultType.FAIL,"3001", "IP가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final ResultType resultType;
    private final String code;
    private final String message;
}
