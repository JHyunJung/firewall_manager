package com.crosscert.firewall.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends BaseTimeEntity{

    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String eMail;
    @Column
    private String password;

    @OneToOne
    private IP devIp;

    @OneToOne
    private IP netIp;

    @OneToMany
    private List<FireWall> fireWallList;

}
