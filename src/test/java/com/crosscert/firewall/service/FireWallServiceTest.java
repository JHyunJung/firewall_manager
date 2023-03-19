package com.crosscert.firewall.service;

import com.crosscert.firewall.config.DatabaseCleanup;
import com.crosscert.firewall.repository.FireWallRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class FireWallServiceTest {

    @Autowired
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


}