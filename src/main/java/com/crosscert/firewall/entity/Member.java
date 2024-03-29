package com.crosscert.firewall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "devIp_id")
    private Ip devIp;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "netIp_id")
    private Ip netIp;

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<FireWall> fireWallList = new ArrayList<>();



    public void addFireWall(FireWall fireWall){
        this.fireWallList.add(fireWall);
        fireWall.setMember(this);
    }

    public String getDevIpValue(){
        return this.devIp == null ? null : devIp.getAddressValue();
    }

    public String getNetIpValue(){
        return this.netIp == null ? null : netIp.getAddressValue();
    }

    public void edit(Role role, Ip devIp, Ip netIp) {
        this.role = role;
        this.devIp = devIp;
        this.netIp = netIp;
    }

    public void setDevIpByAddress(String devIp, String who) {
        if(devIp == null || devIp.isBlank()) return;
        this.devIp = new Ip(devIp, who + " 개발망");
    }

    public void setNetIpByAddress(String netIp, String who) {
        if(netIp == null || netIp.isBlank()) return;
        this.netIp = new Ip(netIp, who + " 인터넷망");
    }

    public void editDevIpDescription(String description) {
        if(this.devIp != null) this.devIp.setDescription(description);
    }

    public void editNetIpDescription(String description) {
        if(this.netIp != null) this.netIp.setDescription(description);
    }

    public void editIp(Ip devIp, Ip netIp) {
        this.devIp = devIp;
        this.netIp = netIp;
    }

    public void editPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
