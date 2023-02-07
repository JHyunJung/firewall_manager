package com.crosscert.firewall.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@NoArgsConstructor
@Embeddable
@Getter
public class IpAddress {

    private String address;
    public IpAddress(String address) {
        if(!isIpAddress(address)){
            throw new IllegalArgumentException("올바른 IP 주소가 아닙니다.");
        }
        this.address = address;
    }

    private boolean isIpAddress(String address){
        String ipAddressPattern = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
        return Pattern.matches(ipAddressPattern, address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpAddress ipAddress = (IpAddress) o;
        return Objects.equals(address, ipAddress.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
