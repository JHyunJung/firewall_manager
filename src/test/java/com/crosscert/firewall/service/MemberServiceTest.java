package com.crosscert.firewall.service;

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
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    IPRepository ipRepository;

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("Member FindAll 5명")
    public void findAll() {
        //given
        IpAddress ipAddress = new IpAddress("172.12.40.52");

        IP ip = IP.builder()
                .domain("domain")
                .description("description")
                .address(ipAddress)
                .build();
        ipRepository.save(ip);

        List<MemberDTO.Response.Public> members = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Member member = Member.builder()
                    .name("name" + i)
                    .email("testData" + i + "@naver.com")
                    .password("123456" + i)
                    .role(Role.MEMBER)
                    .devIp(ip)
                    .netIp(ip)
                    .fireWallList(new ArrayList<>())
                    .build();

            memberRepository.save(member);

            members.add(new MemberDTO.Response.Public(
                    member.getId(),
                    member.getName(),
                    member.getEmail(),
                    member.getRole(),
                    member.getDevIp().getAddress().getAddress(),
                    member.getNetIp().getAddress().getAddress()));
        }

        //when
        List<MemberDTO.Response.Public> memberList = memberService.findAll();

        //then
        Assertions.assertEquals(5, memberList.size());
        Assertions.assertTrue(members.equals(memberList));
    }

    @Test
    @DisplayName("Member FindAll 0명")
    void findAll_0() {
        //given
        //

        //when
        List<MemberDTO.Response.Public> members = memberService.findAll();

        //then
        Assertions.assertEquals(0, members.size());
    }

    @Test
    @DisplayName("editMember Test")
    void editMemberTest() {
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