package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.FireWall;
import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpPort;
import com.crosscert.firewall.entity.search.SearchFirewall;
import com.crosscert.firewall.repository.FirewallRepository;
import javafx.beans.binding.ObjectExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.mysema.commons.lang.Assert.assertThat;
import static com.mysema.commons.lang.Assert.isTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FirewallServiceTest {

    @Autowired
    FirewallRepository firewallRepository;

    @Autowired
    FirewallService firewallService;

    @Test
    void testSearchPage() {
        SearchFirewall search = new SearchFirewall();

        Pageable page = PageRequest.of(0, 10);
        Page<FireWall> result = firewallRepository.searchPage(search, page);

        Assertions.assertNotNull(result, "Result not null");
        Assertions.assertEquals(5, result.getSize(), "Result size 5");
        Assertions.assertEquals(3, result.getTotalPages(), "Total pages 3");

    }

    @Test
    public void testDetailFireWall() {
        Long firewallId = 1L;
        FireWall fireWall = new FireWall();
        fireWall.setId(firewallId);
        fireWall.setEnded(true);
        fireWall.setStart("121.22.22.22");
        fireWall.setDestination("123.12.22.22");
        fireWall.setPort(8080);

        firewallRepository.save(fireWall);


        FireWall result = firewallService.detailFireWall(firewallId);
        Assertions.assertTrue(result.getEnded());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(),firewallId);
        Assertions.assertEquals(result.getEnded(),true);
        Assertions.assertEquals(result.getStart(),"121.22.22.22");
        Assertions.assertEquals(result.getDestination(),"123.12.22.22");
        Assertions.assertEquals(result.getPort(),8080);
    }


    @Test
    void testSaveFireWall() {

        FireWall fireWall = new FireWall();
        fireWall.setEnded(true);
        fireWall.setStart("121.22.22.22");
        fireWall.setDestination("123.12.22.22");
        fireWall.setPort(8080);

        FireWall savedFireWall = firewallService.saveFireWall(fireWall);


        Assertions.assertNotNull(savedFireWall);
        Assertions.assertEquals(fireWall, savedFireWall);
    }


}