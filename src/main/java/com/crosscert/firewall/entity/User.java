package com.crosscert.firewall.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class User {

    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String eMail;
    @Column
    private String password;

    private IP devIp;

    private IP netIp;

    private List<FireWall> fireWallList;

}
