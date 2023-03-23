package com.crosscert.firewall.controller;

import com.crosscert.firewall.config.DatabaseCleanup;
import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.Role;
import com.crosscert.firewall.repository.MemberRepository;
import com.crosscert.firewall.service.MemberService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("LoginController_테스트")
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    public void beforeEach() {

        databaseCleanup.execute();

        MemberDTO.Request.Create createDto = new MemberDTO.Request.Create(
                "test", "test@crosscert.com", "password", Role.MEMBER, "172.77.0.1", "172.77.0.2");
        memberService.signup(createDto);
    }

    @Test
    public void 로그인성공() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "test@crosscert.com")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void 로그인실패_비밀번호_오류() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "test@crosscert.com")
                        .param("password", "wrong_password"))   //비밀번호 틀림.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    @DisplayName("아이디_중복_체크")
    public void 아이디_중복_체크() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/signup/checkDuplicateEmail")
                        .param("email", "test@crosscert.com"))
                .andExpect(status().is2xxSuccessful());

        resultActions.andExpect(jsonPath("result").value("true"));

        resultActions = mockMvc.perform(get("/signup/checkDuplicateEmail")
                        .param("email", "test2@crosscert.com"))
                .andExpect(status().is2xxSuccessful());

        resultActions.andExpect(jsonPath("result").value("false"));
    }

    @Test
    @DisplayName("IP_중복_체크")
    public void IP_중복_체크() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/signup/checkDuplicateIpAddress")
                        .param("ipAddress", "172.77.0.1"))
                .andExpect(status().is2xxSuccessful());

        resultActions.andExpect(jsonPath("result").value("true"));

        resultActions = mockMvc.perform(get("/signup/checkDuplicateIpAddress")
                        .param("ipAddress", "172.77.0.3"))
                .andExpect(status().is2xxSuccessful());

        resultActions.andExpect(jsonPath("result").value("false"));
    }


}