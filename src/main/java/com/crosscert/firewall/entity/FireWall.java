package com.crosscert.firewall.entity;

import com.crosscert.firewall.dto.FireWallDTO.Request.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "start_ip_id")
    private Ip start;

    @Column
    private String destination;

    @Column()
    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "fireWall")
    private List<IpPort> ipPorts = new ArrayList<>();

    @Column
    private LocalDate endDate;

    @Column
    private Boolean isEnded;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public void setMember(Member member){
        this.member = member;
    }
    public void addPort(IpPort port){
        this.ipPorts.add(port);
        port.setFireWall(this);
    }

    public static FireWall toEntity(Create dto, Member member){
        return FireWall.builder()
                .start(Ip.of(dto.getStart()))
                .destination(dto.getDestination())
                .ipPorts(dto.getPort())
                .endDate(dto.getEndDate())
                .isEnded(false)
                .member(member)
                .build();
    }

}
