package com.crosscert.firewall.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class IpPort {

    private int port;

    public IpPort (int port){
        if(!isPortRange(port)){
            throw new IllegalArgumentException("올바른 포트 숫자가 아닙니다.");
        }
        this.port = port;
    }

    private boolean isPortRange(int port){
        return port >= 0 && port <= 65535;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpPort ipPort = (IpPort) o;
        return port == ipPort.port;
    }

    @Override
    public int hashCode() {
        return Objects.hash(port);
    }

}
