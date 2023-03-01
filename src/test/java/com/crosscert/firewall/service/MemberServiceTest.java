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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    IPRepository ipRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

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

        List<Member> members = new ArrayList<>();

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
            members.add(member);
        }

        //when
        List<Member> memberList = memberService.findAllFetch();

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
        List<Member> members = memberService.findAllFetch();

        //then
        Assertions.assertEquals(0, members.size());
    }

    @Test
    @DisplayName("changeResDto Test")
    void changeResDtoTest() {
        //given
        IpAddress ipAddress = new IpAddress("172.12.40.52");

        IP ip = IP.builder()
                .domain("domain")
                .description("description")
                .address(ipAddress)
                .build();

        Member member = Member.builder()
                .name("name")
                .email("testData"  + "@naver.com")
                .password("123456")
                .role(Role.MEMBER)
                .devIp(ip)
                .netIp(ip)
                .fireWallList(new ArrayList<>())
                .build();

        //when
        MemberDTO.Response.Public publicDto = memberService.changeResDto(member);

        //then
        assertEquals("name",publicDto.getName());
        assertEquals("testData@naver.com",publicDto.getEmail());
        assertEquals(Role.MEMBER,publicDto.getRole());
        assertEquals("172.12.40.52",publicDto.getDevIp());
        assertEquals("172.12.40.52",publicDto.getNetIp());
    }

    @Test
    @DisplayName("changeResDto Without Ip Test")
    void changeResDtoWithoutIpTest() {
        //given
        Member member = Member.builder()
                .name("name")
                .email("testData"  + "@naver.com")
                .password("123456")
                .role(Role.MEMBER)
                .devIp(null)
                .netIp(null)
                .fireWallList(new ArrayList<>())
                .build();

        //when
        MemberDTO.Response.Public publicDto = memberService.changeResDto(member);

        //then
        assertEquals("name",publicDto.getName());
        assertEquals("testData@naver.com",publicDto.getEmail());
        assertEquals(Role.MEMBER,publicDto.getRole());
        assertEquals("",publicDto.getDevIp());
        assertEquals("",publicDto.getNetIp());
    }

    @Test
    public void 정상적인_회원가입() {
        //given
        MemberDTO.Request.Create createDto = MemberDTO.Request.Create.builder()
                .email("test@crosscert.com")
                .name("test")
                .password("password")
                .devIp("172.77.0.1")
                .netIp("172.77.0.2")
                .role(Role.MEMBER)
                .build();

        //when
        memberService.signup(createDto);

        //then
        Member findMember = memberRepository.findByEmail(createDto.getEmail()).orElseThrow(() -> new IllegalStateException("회원정보 없음"));
        assertEquals(createDto.getName(),findMember.getName());
        assertTrue(passwordEncoder.matches(createDto.getPassword(), findMember.getPassword()));
        assertEquals(createDto.getDevIp(),findMember.getDevIp().getAddress().getAddress());
        assertEquals(createDto.getNetIp(),findMember.getNetIp().getAddress().getAddress());
        assertEquals(createDto.getRole(),findMember.getRole());
    }

    @Test
    public void 중복회원가입시_IllegalStateException() {
        //given
        MemberDTO.Request.Create createDto = MemberDTO.Request.Create.builder()
                .email("test2@crosscert.com")
                .name("test2")
                .password("password1")
                .devIp("172.77.0.1")
                .netIp("172.77.0.2")
                .role(Role.MEMBER)
                .build();
        memberService.signup(createDto);

        MemberDTO.Request.Create createDto2 = MemberDTO.Request.Create.builder()
                .email("test2@crosscert.com")   //이메일(아이디) 중복
                .name("test3")
                .password("password3")
                .devIp("172.77.0.3")
                .netIp("172.77.0.4")
                .role(Role.MEMBER)
                .build();

        //when & then
        assertThatThrownBy(() -> memberService.signup(createDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 존재하는 회원입니다.");
    }

    @Test
    public void 이메일중복체크_중복일때_true() {
        // Given
        MemberDTO.Request.Create createDto = MemberDTO.Request.Create.builder()
                .email("test4@crosscert.com")
                .name("test4")
                .password("password4")
                .devIp("172.77.0.1")
                .netIp("172.77.0.2")
                .role(Role.MEMBER)
                .build();
        memberService.signup(createDto);

        String email =  "test5@crosscert.com";
        String email2 = "test4@crosscert.com";   //이메일 중복

        // When
        boolean result = memberService.isPresentMember(email);
        boolean result2 = memberService.isPresentMember(email2);

        // Then
        assertFalse(result);
        assertTrue(result2);
    }

}