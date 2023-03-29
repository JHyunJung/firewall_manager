package com.crosscert.firewall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class IpPort extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int port;

    @ManyToOne
    @JoinColumn(name = "firewall_id")
    private FireWall fireWall;

    public IpPort (int port){
        if(!isPortRange(port)){
            throw new IllegalArgumentException("올바른 포트 숫자가 아닙니다.");
        }
        this.port = port;
    }

    private boolean isPortRange(int port){
        return port >= 0 && port <= 65535;
    }

    public void setFireWall(FireWall fireWall){
        this.fireWall = fireWall;
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
