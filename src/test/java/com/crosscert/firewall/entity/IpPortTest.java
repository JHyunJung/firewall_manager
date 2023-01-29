package com.crosscert.firewall.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("IpPort 클래스 테스트")
public class IpPortTest {

    @Test
    @DisplayName("생성자_테스트")
    public void 생성자_테스트(){
        int port1 = 8080;
        int port2 = 6000;

        IpPort ipPort1 = new IpPort(port1);
        IpPort ipPort2 = new IpPort(port2);

        assertEquals(ipPort1, new IpPort(port1));
        assertEquals(ipPort2, new IpPort(port2));
    }
    
    @Test
    @DisplayName("생성자_valid_테스트")
    public void 생성자_valid_테스트(){
        //given
        int port1 = -1;
        int port2 = 65536;
        assertThatThrownBy(() -> {
            new IpPort(port1);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바른 포트 숫자가 아닙니다.");

        assertThatThrownBy(() -> {
            new IpPort(port2);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바른 포트 숫자가 아닙니다.");
    }
}
