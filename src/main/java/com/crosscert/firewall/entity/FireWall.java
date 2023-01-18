package com.crosscert.firewall.entity;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class FireWall {

    private Long id;
    private IP start;
    private IP destination;
    private int port;
    private LocalDate endDate;
    private Boolean isEnded;
    User user;

}
