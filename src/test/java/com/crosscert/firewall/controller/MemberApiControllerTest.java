package com.crosscert.firewall.controller;

import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.entity.Role;
import com.crosscert.firewall.repository.IPRepository;
import com.crosscert.firewall.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    IPRepository ipRepository;

    @Autowired
    MemberApiController memberController;

    private Member member;

    @BeforeEach
    void init() {
        IpAddress ipAddress = new IpAddress("172.12.40.52");

        IP ip = IP.builder()
                .domain("domain")
                .description("description")
                .address(ipAddress)
                .build();
        ipRepository.save(ip);

        member = Member.builder()
                .name("name")
                .email("hi"+"@naver.com")
                .password("123456")
                .role(Role.MEMBER)
                .devIp(ip)
                .netIp(ip)
                .fireWallList(new ArrayList<>())
                .build();

        memberRepository.save(member);
    }

    @Nested
    @DisplayName("멤버 수정 테스트")
    class editMember {
        @Test
        @DisplayName("멤버 수정 테스트 성공")
        void ok() throws Exception {

            //given
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("name", "testName");
            params.add("email", "test@naver.com");
            params.add("role", "LEADER");
            params.add("devIp", "172.12.40.52");
            params.add("netIp", "172.12.40.52");

            //when then
            ResultActions result = mockMvc.perform(put("/member/{id}", member.getId())
                            .params(params)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            String responseBody = result.andReturn().getResponse().getContentAsString();
            Assertions.assertEquals("OK",responseBody);
        }


        @Test
        @DisplayName("멤버 수정 테스트 실패 - ip가 없을 때")
        void fail() {

            //given
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("name", "testName");
            params.add("email", "test@naver.com");
            params.add("role", "LEADER");
            params.add("devIp", "172.12.40.51");
            params.add("netIp", "172.12.40.51");

            //when then
            try {
                mockMvc.perform(put("/member/{id}", member.getId())
                        .params(params)
                        .accept(MediaType.APPLICATION_JSON));
            } catch (Exception e) {
                Assertions.assertTrue(e.getCause() instanceof NullPointerException);
                Assertions.assertEquals("해당 IP가 존재하지 않습니다",e.getCause().getMessage());
            }
        }

    }




}