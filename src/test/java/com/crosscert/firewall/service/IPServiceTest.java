package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.repository.IPRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
class IPServiceTest {

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
            ipService.findById(2L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 IP가 존재하지 않습니다.");
    }
}