package com.crosscert.firewall.service;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Role;
import com.crosscert.firewall.repository.IPRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class IPServiceTest {

    @Autowired
    IPRepository ipRepository;

    @Autowired
    IPService ipService;

    @BeforeEach
    void clean(){
        ipRepository.deleteAll();
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
            ipService.findById(2L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 IP가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("isPresentIp Test")
    void isPresentIp() {
        // Given
        IP ip = IP.builder()
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