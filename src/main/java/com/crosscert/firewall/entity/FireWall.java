package com.crosscert.firewall.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class FireWall extends BaseTimeEntity{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private IP start;

    @ManyToOne
    private IP destination;

    @Column
    private int port;

    @Column
    private LocalDate endDate;

    @Column
    private Boolean isEnded;

    @ManyToOne
    private User user;

}
