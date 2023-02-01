package com.crosscert.firewall.service;

import com.crosscert.firewall.repository.LogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogServiceTest {

    @Autowired
    LogRepository logRepository;

    @Autowired
    LogService logService;

    @Test
    @DisplayName("find_exception")
    public void find_exception() {
        assertThatThrownBy(() -> {
            logService.findById(2L);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("없는 아이디 입니다.");
    }
}