package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.FireWall;
import com.crosscert.firewall.repository.FireWallRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class FirewallServiceTest {

    @Autowired
    FireWallRepository firewallRepository;

    @Autowired
    FirewallService firewallService;
    


}