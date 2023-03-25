package com.crosscert.firewall.service;

import com.crosscert.firewall.config.DatabaseCleanup;
import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.entity.Role;
import com.crosscert.firewall.repository.IpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class IpServiceTest {

    @Autowired
    IpRepository ipRepository;

    @Autowired
    IpService ipService;

    @Autowired
    MemberService memberService;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void clean(){
        databaseCleanup.execute();
    }

    @Test
    @DisplayName("findByAddress Exception")
    void findByAddressNO() {
        assertThatThrownBy(() -> {
            ipService.findByAddress(new IpAddress("111.111.111.111"));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 IP가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("findById Exception")
    void findByIdNO() {
        assertThatThrownBy(() -> {
            ipService.findById(1L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 IP가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("isPresentIp Test")
    void isPresentIp() {
        // Given
        Ip ip = Ip.builder()
                .domain("testdomain.com")
                .description("테스트 IP")
                .address(new IpAddress("172.77.0.1"))
                .build();
        ipService.save(ip);


        // When
        String ip1 =  "172.77.0.3";
        String ip2 = "172.77.0.1";   //IP 중복

        // Then
        assertFalse(ipService.isPresentIp(new IpAddress(ip1)));
        assertTrue(ipService.isPresentIp(new IpAddress(ip2)));  //IP 중복시 true
    }


    @Test
    @DisplayName("Member가 사용하지 않고 있는 IP 리스트 조회 테스트")
    public void findAllWithoutMemberTest() {
        // Given
        List<Ip> expectedIpList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .name("name" + i)
                    .email("testData" + i + "@crosscert.com")
                    .password("123456" + i)
                    .role(Role.MEMBER)
                    .build();
            member.setDevIpByAddress("11.1.1."+i,"name" + i);
            member.setDevIpByAddress("22.2.2."+i,"name" + i);
            memberService.save(member);

            //Member가 없는 IP
            Ip ip = Ip.builder()
                    .domain(i+"TestDomain.com")
                    .description("no Member Test Ip "+i)
                    .address(new IpAddress("33.3.3."+i))
                    .build();
            ipService.save(ip);
            expectedIpList.add(ip);
        }

        // When
        List<Ip> actualIpList = ipService.findAllWithoutMember();

        // Then
        assertEquals(expectedIpList.size(), actualIpList.size());
        assertIterableEquals(expectedIpList, actualIpList);
    }

    @Test
    @DisplayName("IP 자동 할당(IP 신규생성 or 미할당된 IP 사용)")
    public void testAllocateIp() {
        // Given
        String address = "33.33.33.33";
        String description = "존재하지만 미할당된 IP";
        Ip existingIp = new Ip(address, description);
        ipRepository.save(existingIp);

        // When
        Ip allocatedIp1 = ipService.allocateIp("11.11.11.11", "신규");
        ipRepository.save(allocatedIp1);
        Ip allocatedIp2 = ipService.allocateIp("33.33.33.33", "할당");

        // Then
        Ip findIp1 = ipService.findByAddress(new IpAddress("11.11.11.11"));
        Ip findIp2 = ipService.findByAddress(new IpAddress("33.33.33.33"));

        assertNotNull(findIp1);
        assertEquals(allocatedIp1, findIp1);
        assertEquals(allocatedIp2, findIp2);
        assertEquals(allocatedIp2.getDescription(), findIp2.getDescription());
    }

    @Test
    @DisplayName("이미 할당된 IP 요청시 예외발생")
    public void testAllocateIpException() {
        // Given
        Member member = Member.builder()
                .name("홍길동")
                .email("test@crosscert.com")
                .password("123456")
                .role(Role.MEMBER)
                .build();
        member.setDevIpByAddress("22.22.22.22","홍길동 개발망");
        memberService.save(member);

        entityManager.clear();

        //when & then
        assertThatThrownBy(() -> ipService.allocateIp("22.22.22.22","마이클 개발망"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 IP는 다른 이용자가 사용중입니다.");
    }
}