package com.crosscert.firewall.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FireWall extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "start_ip")
    private Ip start;

    @ManyToOne
    @JoinColumn(name = "destination_ip")
    private Ip destination;

    @Embedded
    private IpPort port;

    @Column
    private LocalDate endDate;

    @Column
    private Boolean isEnded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
