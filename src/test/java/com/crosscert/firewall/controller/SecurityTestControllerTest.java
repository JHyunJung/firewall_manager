package com.crosscert.firewall.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("스프링시큐리티 테스트")
class SecurityTestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("모두 접근 가능한 페이지")
    void index() throws Exception {
        mockMvc.perform(get("/sec")
                .with(anonymous()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인시에만 접근 가능한 페이지")
    void memberTestpage() throws Exception {
        mockMvc.perform(get("/sec/member/test")
                        .with(user("testuser").roles("MEMBER")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("leader 권한 로그인시에만 접근 가능한 페이지")
    void leaderTestpage() throws Exception {
        mockMvc.perform(get("/sec/leader/test")
                        .with(user("testuser").roles("LEADER")))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("로그인 안하고 접근시")
    void noLoginTest() throws Exception {
        mockMvc.perform(get("/sec/member/test")
                        .with(anonymous()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("member 권한만 가지고 있는 유저가 leader 권한 페이지 접근시")
    void noLeaderTest() throws Exception {
        mockMvc.perform(get("/sec/leader/test")
                        .with(user("testuser").roles("MEMBER")))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}