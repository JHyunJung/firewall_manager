package com.crosscert.firewall.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IpAddress 클래스 테스트")
class IpAddressTest {

    @Test
    @DisplayName("IpAddress 생성자 테스트")
    public void 생성자_테스트(){
        String testAddress1 = "0.0.0.0";
        String testAddress2 = "255.255.255.255";

        IpAddress address1 = new IpAddress(testAddress1);
        IpAddress address2 = new IpAddress(testAddress2);

        assertEquals(address1, new IpAddress(testAddress1));
        assertEquals(address2, new IpAddress(testAddress2));
    }

    @Test
    @DisplayName("valid_test")
    public void valid_test(){
        String testAddress1 = "-1.-1.-1.-1";
        String testAddress2 = "266.266.266.266";

        assertThatThrownBy(() -> {
            new IpAddress(testAddress1);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바른 IP 주소가 아닙니다.");

        assertThatThrownBy(() -> {
            new IpAddress(testAddress2);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바른 IP 주소가 아닙니다.");
    }

}