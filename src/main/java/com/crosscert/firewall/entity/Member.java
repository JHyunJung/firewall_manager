package com.crosscert.firewall.entity;

import com.crosscert.firewall.dto.MemberDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@ToString
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "devIp_id")
    private IP devIp;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "netIp_id")
    private IP netIp;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<FireWall> fireWallList = new ArrayList<>();

    public String getDevIpValue(){
        return this.devIp == null ? null : devIp.getAddressValue();
    }

    public void edit(MemberDTO.Request.EditInfo memberDTO, IP devIp, IP netIp) {
        this.name = memberDTO.getName();
        this.email = memberDTO.getEmail();
        this.role = memberDTO.getRole();
        this.devIp = devIp;
        this.netIp = netIp;
    }

    public void setDevIpByAddress(String devIp) {
        this.devIp = new IP(devIp);
    }

    public void setNetIpByAddress(String netIp) {
        this.netIp = new IP(netIp);
    }
}
