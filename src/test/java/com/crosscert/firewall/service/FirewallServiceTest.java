package com.crosscert.firewall.service;

import com.crosscert.firewall.config.DatabaseCleanup;
import com.crosscert.firewall.entity.FireWall;
<<<<<<< HEAD
import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.search.SearchFirewall;
import com.crosscert.firewall.repository.FireWallRepository;
import com.crosscert.firewall.repository.FirewallRepository;
import javafx.beans.binding.ObjectExpression;
=======
import com.crosscert.firewall.repository.FireWallRepository;
>>>>>>> main
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

<<<<<<< HEAD
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

=======
>>>>>>> main
@SpringBootTest
class FireWallServiceTest {

    @Autowired
<<<<<<< HEAD
    FireWallRepository fireWallRepository;

    @Autowired
    FireWallService fireWallService;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @BeforeEach
    void clean(){
        databaseCleanup.execute();
    }

    @Test
    @DisplayName("findById Exception")
    void findByIdException() {
        assertThatThrownBy(() -> {
            fireWallService.findById(1L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 방확벽이 존재하지 않습니다.");
    }
=======
    FireWallRepository firewallRepository;

    @Autowired
    FirewallService firewallService;
    
>>>>>>> main


}