package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.repository.IPRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
class IpServiceTest {

    @Autowired
    IPRepository ipRepository;

    @Autowired
    IPService ipService;

    @Test
    @DisplayName("findByAddress Exception")
    void findByAddressNO() {
        assertThatThrownBy(() -> {
            ipService.findByAddress(new IpAddress("111.111.111.111"));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 IP가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("findById Exception")
    void findByIdNO() {
        assertThatThrownBy(() -> {
            ipService.findById(1L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 IP가 존재하지 않습니다.");
    }
}