package com.crosscert.firewall.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class IP extends BaseTimeEntity{

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private IpAddress address;

    @Column
    private String domain;

    @Column
    private String description;
}
