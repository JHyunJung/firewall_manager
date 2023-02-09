package com.crosscert.firewall.exception;

import com.crosscert.firewall.entity.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    @NotBlank
    private HttpStatus httpStatus;
    @NotBlank
    private ResultType resultType;
    @NotBlank
    private String code;
    @NotBlank
    private String message;


}
