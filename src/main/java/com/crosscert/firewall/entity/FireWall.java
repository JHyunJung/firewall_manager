package com.crosscert.firewall.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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


    public Long getId() {
        return id;
    }

    public IP getStart() {
        return start;
    }

    public IP getDestination() {
        return destination;
    }

    public IpPort getPort() {
        return port;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Boolean getEnded() {
        return isEnded;
    }

    public Member getMember() {
        return member;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IP setStart(String start) {
          return  new IP(start);
    }
    public void setStart(IP start) {
        this.start = start;
    }
    public IP setDestination(String destination) {
        return  new IP(destination);
    }
    public void setDestination(IP destination) {
        this.destination = destination;
    }

    public IpPort setPort(int port) {
        return  new IpPort(port);
    }

    public void setPort(IpPort port) {
        this.port = port;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setEnded(Boolean ended) {
        isEnded = ended;
    }
}
