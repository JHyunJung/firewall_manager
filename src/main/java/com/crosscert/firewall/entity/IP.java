package com.crosscert.firewall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class IP extends BaseTimeEntity{

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String address;

    @Column
    private String domain;

    @Column
    private String description;
}
