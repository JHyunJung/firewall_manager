package com.crosscert.firewall.dto;

import com.crosscert.firewall.entity.ResultType;
import lombok.Value;

import javax.validation.constraints.NotBlank;

public enum ResDTO {;

    private interface Result {@NotBlank ResultType getResultType(); }

    @Value public static class Public implements Result {
        ResultType resultType;
    }
}
