package com.crosscert.firewall.entity;

import lombok.*;

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
    private IP start;

    @ManyToOne
    @JoinColumn(name = "destination_ip")
    private IP destination;

    @Embedded
    private IpPort port;

    @Column
    private LocalDate endDate;

    @Column
    private Boolean isEnded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public void setEnded(Boolean ended) {
        isEnded = ended;
    }
}
