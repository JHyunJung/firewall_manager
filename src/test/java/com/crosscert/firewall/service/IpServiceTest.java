package com.crosscert.firewall.service;

import com.crosscert.firewall.config.DatabaseCleanup;
import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.repository.IPRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class IpServiceTest {

    @Autowired
    IPRepository ipRepository;

    @Autowired
    IPService ipService;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @BeforeEach
    void clean(){
        databaseCleanup.execute();
    }

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

    @Test
    @DisplayName("isPresentIp Test")
    void isPresentIp() {
        // Given
        Ip ip = Ip.builder()
                .domain("testdomain.com")
                .description("테스트 IP")
                .address(new IpAddress("172.77.0.1"))
                .build();
        ipService.save(ip);


        // When
        String ip1 =  "172.77.0.3";
        String ip2 = "172.77.0.1";   //IP 중복

        // Then
        assertFalse(ipService.isPresentIp(new IpAddress(ip1)));
        assertTrue(ipService.isPresentIp(new IpAddress(ip2)));  //IP 중복시 true
    }
}