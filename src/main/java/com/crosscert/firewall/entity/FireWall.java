package com.crosscert.firewall.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class FireWall extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "start_ip_id")
    private Ip start;

    @Column
    private String destination;

    @OneToMany(mappedBy = "fireWall")
    private List<IpPort> port = new ArrayList<>();

    @Column
    private LocalDate endDate;

    @Column
    private Boolean isEnded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
