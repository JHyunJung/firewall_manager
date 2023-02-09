package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.entity.Role;
import com.crosscert.firewall.repository.IPRepository;
import com.crosscert.firewall.repository.MemberRepository;
import com.crosscert.firewall.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

@SpringBootTest
@Transactional
class MemberApiControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    IPRepository ipRepository;

    @Autowired
    MemberApiController memberController;

    @Test
    @DisplayName("멤버 수정 테스트")
    void editMember() {

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


        String name = "testName";
        String testEmail = "test@naver.com";
        String testIpAddress = "172.12.40.52";
        MemberDTO.Request.EditInfo memberDTO = new MemberDTO.Request.EditInfo(name, testEmail, Role.LEADER, testIpAddress, testIpAddress);

        //when
        memberController.edit(saveMember.getId(), memberDTO);
        Member findMember = memberRepository.findById(saveMember.getId()).get();

        //then
        Assertions.assertEquals(name,findMember.getName());
        Assertions.assertEquals(testEmail,findMember.getEmail());
        Assertions.assertEquals(Role.LEADER,findMember.getRole());
    }
}