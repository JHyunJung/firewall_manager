package com.crosscert.firewall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Getter
public class Ip extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private IpAddress address;

    @Column
    private String domain;

    @Column
    private String description;

    @OneToOne(mappedBy = "devIp")
    private Member devMember;

    @OneToOne(mappedBy = "netIp")
    private Member netMember;
    public Ip(String ipAddress, String description) {
        this.address = new IpAddress(ipAddress);
        this.description = description;
    }
    public String getAddressValue(){
        return this.address == null ? null : this.address.getAddress();
    }

    public Ip(IpAddress address, String domain, String description, Member devMember, Member netMember) {
        this.address = address;
        this.domain = domain;
        this.description = description;
        this.devMember = devMember;
        this.netMember = netMember;
    }

    public void memberUpdate(Member member) {
        this.devMember = member;
        this.netMember = member;
    }



}
