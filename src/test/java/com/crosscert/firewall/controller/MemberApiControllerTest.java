package com.crosscert.firewall.controller;

import com.crosscert.firewall.config.DatabaseCleanup;
import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.entity.Role;
import com.crosscert.firewall.repository.IPRepository;
import com.crosscert.firewall.repository.MemberRepository;
import com.crosscert.firewall.service.IPService;
import com.crosscert.firewall.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @Autowired
    IPService ipService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    IPRepository ipRepository;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    private Member member;

    @BeforeEach
    void init() {

        databaseCleanup.execute();

        IpAddress ipAddress = new IpAddress("172.12.40.52");

        Ip ip = Ip.builder()
                .domain("domain")
                .description("description")
                .address(ipAddress)
                .build();

        Ip updatedIp1 = Ip.builder()
                .domain("domain")
                .description("description")
                .address(new IpAddress("222.222.222.222"))
                .build();

        Ip updatedIp2 = Ip.builder()
                .domain("domain")
                .description("description")
                .address(new IpAddress("123.123.123.123"))
                .build();


        member = Member.builder()
                .name("name")
                .email("hi@naver.com")
                .password("123456")
                .role(Role.MEMBER)
                .devIp(ip)
                .netIp(ip)
                .fireWallList(new ArrayList<>())
                .build();

        ipService.save(updatedIp1);
        ipService.save(updatedIp2);
        memberService.save(member);
    }

    @Nested
    @DisplayName("멤버 수정 테스트")
    class editMember {
        @Test
        @DisplayName("멤버 수정 테스트 성공")
        void ok() throws Exception {

            //given
            MemberDTO.Request.Edit editDto = new MemberDTO.Request.Edit(
                    Role.LEADER, "222.222.222.222", "123.123.123.123"
            );
            ObjectMapper mapper = new ObjectMapper();
            //when then
            ResultActions result = mockMvc.perform(put("/api/member/{id}", member.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(editDto)));

            result.andExpect(status().isOk())
                    .andExpect(jsonPath("role").value("LEADER"))
                    .andExpect(jsonPath("devIp").value("222.222.222.222"))
                    .andExpect(jsonPath("netIp").value("123.123.123.123"));
        }

        @Test
        @DisplayName("멤버 수정 테스트 실패 - ip가 없을 때")
        void fail() throws Exception {

            MemberDTO.Request.Edit editDto = new MemberDTO.Request.Edit(
                    Role.LEADER, "111.111.111.111", null
            );

            //when then
            ObjectMapper mapper = new ObjectMapper();

            assertThrows(NestedServletException.class, () ->{
                mockMvc.perform(put("/api/member/{id}", member.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(editDto)));
            });


//            result.andExpect(status().is4xxClientError());
        }
    }

}