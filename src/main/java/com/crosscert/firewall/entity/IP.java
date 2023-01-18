package com.crosscert.firewall.entity;

import javax.persistence.Entity;

@Entity
public class IP {

    private Long id;
    private String address;
    private String domain;
    private String description;
}
