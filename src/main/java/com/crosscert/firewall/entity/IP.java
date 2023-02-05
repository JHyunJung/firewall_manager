package com.crosscert.firewall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Getter
public class IP extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}
