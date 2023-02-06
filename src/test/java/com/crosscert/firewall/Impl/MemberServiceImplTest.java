package com.crosscert.firewall.Impl;

import com.crosscert.firewall.dto.MemberDTO;
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
class MemberServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    IPRepository ipRepository;

    @Autowired
    MemberServiceImpl memberService;

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
        String testIpAddress = "177.77.77.77";
        MemberDTO.Request.EditInfo memberDTO = new MemberDTO.Request.EditInfo(name, testEmail, Role.LEADER, testIpAddress, testIpAddress);

        //when
        memberService.editMember(saveMember.getId(), memberDTO);
        Member findMember = memberRepository.findById(saveMember.getId()).get();

        //then
        Assertions.assertEquals(name,findMember.getName());
        Assertions.assertEquals(testEmail,findMember.getEmail());
        Assertions.assertEquals(Role.LEADER,findMember.getRole());
        Assertions.assertEquals(testIpAddress,findMember.getDevIp().getAddress().getAddress());
        Assertions.assertEquals(testIpAddress, findMember.getNetIp().getAddress().getAddress());

    }
}