package com.crosscert.firewall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
    @OneToOne(mappedBy = "devIp")
    private Member devMember;

    @JsonBackReference
    @OneToOne(mappedBy = "netIp")
    @JoinColumn(name = "netMember_id")
    private Member netMember;
    public Ip(String ipAddress, String description) {
        this.address = new IpAddress(ipAddress);
        this.description = description;
    }

    public Ip(IpAddress ipAddress){
        this.address = ipAddress;
    }

    public static Ip of(String ipAddress){
        return new Ip(new IpAddress(ipAddress));
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
