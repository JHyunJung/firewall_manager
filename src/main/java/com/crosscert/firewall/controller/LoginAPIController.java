package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
@RequiredArgsConstructor
public class LoginAPIController {

    private final MemberService memberService;

    //회원가입 진행   //TODO: MemberApiController에서 진행할지 결정 필요.
    @PostMapping("/signup")
    @ResponseBody
    public String signup(MemberDTO.Request.Create memberDTO){
        log.info("{}.signup",this.getClass());

        //:TODO 비밀번호 암호화

        log.info("memberDTO : "+memberDTO);

        String newMemberEmail = memberService.signup(memberDTO);

        log.info("newMemberEmail : "+newMemberEmail);

        return newMemberEmail+" 가입 성공!";
    }


}
