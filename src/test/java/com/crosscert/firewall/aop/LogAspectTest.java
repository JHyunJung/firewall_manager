package com.crosscert.firewall.aop;

import com.crosscert.firewall.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;


@Import(LogAspect.class)
@SpringBootTest
class LogAspectTest {

    @Autowired
    MemberService memberService;


    @Test
    @DisplayName("Log_aop_테스트")
    public void Log_aop_테스트(){
        System.out.println("test start");
        memberService.isPresentMember("test@test.com");
    }


}