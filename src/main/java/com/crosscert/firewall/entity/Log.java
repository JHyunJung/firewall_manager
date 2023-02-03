package com.crosscert.firewall.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Method method;

    @Column(nullable = false)
    private String url;

    @Column
    private String param;

    @Column
    private String body;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HttpStatus status;
}
