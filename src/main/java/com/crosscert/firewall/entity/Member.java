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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public void editMember(MemberDTO.Request.EditInfo memberDTO,IP devIp, IP netIp) {
        this.name = memberDTO.getName();
        this.email = memberDTO.getEmail();
        this.role = memberDTO.getRole();
        this.devIp = devIp;
        this.netIp = netIp;
    }

}
