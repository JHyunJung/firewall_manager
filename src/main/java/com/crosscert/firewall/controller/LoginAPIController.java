package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.dto.SignUpDTO;
import com.crosscert.firewall.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
public class LoginAPIController {

    private final MemberService memberService;

    public LoginAPIController(MemberService memberService) {
        this.memberService = memberService;
    }

    //회원가입 진행   //TODO: MemberApiController에서 진행할지 결정 필요.
    @PostMapping("/signup")
    @ResponseBody
    public String signup(SignUpDTO signUpDTO){  //비밀번호 암호화 진행
        log.info("{}.signup",this.getClass());
        log.info("signUpDTO : "+signUpDTO);

        MemberDTO.Request.Create memberDTO = new MemberDTO.Request.Create(
                signUpDTO.getName(),
                signUpDTO.getEmail(),
                signUpDTO.getPassword(),
                signUpDTO.getRole(),
                signUpDTO.getDevIp(),
                signUpDTO.getNetIp()
        );
        log.info("memberDTO : "+memberDTO);

        String newMemberEmail = memberService.signup(memberDTO);

        log.info("newMemberEmail : "+newMemberEmail);

        return newMemberEmail+" 가입 성공!";
    }


}
