package com.crosscert.firewall.service;

import com.crosscert.firewall.config.DatabaseCleanup;
import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.entity.Role;
import com.crosscert.firewall.repository.IPRepository;
import com.crosscert.firewall.repository.MemberRepository;
import org.junit.jupiter.api.*;
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

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Test
    @DisplayName("findById_Exception_테스트")
    public void findById_Exception_테스트(){
        assertThatThrownBy(() -> {
            memberService.findById(100L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 Member가 없습니다.");
    }

    @BeforeEach
    void clean(){
        databaseCleanup.execute();
    }

    @Test
    @DisplayName("Member FindAll")
    public void findAll() {
        //given

        assertEquals(0, memberService.findAllFetch().size());

        Ip ip = Ip.builder()
                .domain("domain")
                .description("description")
                .address(new IpAddress("172.12.40.52"))
                .build();
        ipRepository.save(ip);

        List<Member> testMemberList = new ArrayList<>();

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

            testMemberList.add(member);
            memberService.save(member);
        }

        //when
        List<Member> memberList = memberService.findAllFetch();

        //then
        assertEquals(testMemberList.size(), memberList.size());
        assertIterableEquals(testMemberList, memberList);
    }

    @Test
    @DisplayName("Member_edit_테스트")
    public void Member_edit_테스트(){
        //given

        String name = "test";
        String email = "test@tset.com";
        String password = "test";
        Ip ip = Ip.builder()
                .domain("domain")
                .description("description")
                .address(new IpAddress("172.12.40.52"))
                .build();

        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(Role.MEMBER)
                .devIp(ip)
                .netIp(ip)
                .build();

        Member savedMember = memberService.save(member);
        //when
        Member foundMember = memberService.findById(savedMember.getId());

        Role updatedRole = Role.LEADER;
        Ip updatedNetIp = new Ip("111.111.111.111", "updatedNet");
        Ip updatedDevIp = new Ip("111.111.111.112", "updatedDev");

        memberService.edit(foundMember, updatedRole, updatedDevIp, updatedNetIp);

        //then
        Member updatedMember = memberService.findById(savedMember.getId());

        assertEquals(updatedMember.getRole(), updatedRole);
        assertEquals(updatedMember.getDevIpValue(), updatedDevIp.getAddressValue());
        assertEquals(updatedMember.getNetIpValue(), updatedNetIp.getAddressValue());
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
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 회원입니다.");
    }

    @Test
    @DisplayName("이메일중복체크")
    public void 이메일중복체크() {
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

        // Then
        assertFalse(memberService.isPresentMember(email));
        assertTrue(memberService.isPresentMember(email2));
    }

}