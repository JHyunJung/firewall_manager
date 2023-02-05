package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.entity.Role;
import com.crosscert.firewall.repository.IPRepository;
import com.crosscert.firewall.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@SpringBootTest
@Transactional
class IPServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    IPRepository ipRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    IPService ipService;

    @Test
    @DisplayName("findIpForEdit, 변경 요청 ipAddress 존재하지 않을 때")
    void findIpForEditTest() {
        //given
        IpAddress ipAddress = new IpAddress("172.12.40.52");

        IP ip = IP.builder()
                .domain("domain")
                .description("description")
                .address(ipAddress)
                .build();
        ipRepository.save(ip);

        Member member = Member.builder()
                .name("name")
                .email("hi"+"@naver.com")
                .password("123456")
                .role(Role.MEMBER)
                .devIp(ip)
                .netIp(ip)
                .fireWallList(new ArrayList<>())
                .build();

        Member saveMember = memberRepository.save(member);
        String testIpAddress = "172.12.40.53";

        //when
        IP returnIp = ipService.findIpForEdit(saveMember, testIpAddress);

        //then
        Assertions.assertEquals(testIpAddress,returnIp.getAddress().getAddress());
    }

    @Test
    @DisplayName("findIpForEdit, 변경 요청 ipAddress 존재할 때")
    void findIpForEditTest2() {
        //given
        IpAddress ipAddress = new IpAddress("172.12.40.52");
        IpAddress ipAddress2 = new IpAddress("172.12.40.53");

        IP ip = IP.builder()
                .domain("domain")
                .description("description")
                .address(ipAddress)
                .build();

        IP ip2 = IP.builder()
                .domain("domain")
                .description("description")
                .address(ipAddress2)
                .build();
        ipRepository.save(ip);
        ipRepository.save(ip2);

        Member member = Member.builder()
                .name("name")
                .email("hi"+"@naver.com")
                .password("123456")
                .role(Role.MEMBER)
                .devIp(ip)
                .netIp(ip)
                .fireWallList(new ArrayList<>())
                .build();

        Member saveMember = memberRepository.save(member);
        String testIpAddress = "172.12.40.53";

        //when
        IP returnIp = ipService.findIpForEdit(saveMember, testIpAddress);

        //then
        Assertions.assertEquals(testIpAddress,returnIp.getAddress().getAddress());
    }
}