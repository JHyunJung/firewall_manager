package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
public class LoginController {

    private final MemberService memberService;

    //회원가입 페이지
    @GetMapping("/signup")
    public String signUpPage(){
        return "signup";
    }


    //회원가입 진행
    @PostMapping("/signup")
    public String signup(MemberDTO.Request.Create memberDTO){
        log.info("{}.signup",this.getClass());
        memberService.signup(memberDTO);
        return "redirect:/login?signup=done";   //회원가입 완료 alert를 위한 값
    }


    //이메일 중복체크
    @GetMapping("/checkDuplicateEmail")
    @ResponseBody
    public ResponseEntity<Object> checkDuplicateEmail(@RequestParam String email) {
        log.info("{}.checkDuplicateEmail",this.getClass());
        Map<String, Object> data = new HashMap<>();
        data.put("result", memberService.isPresentMember(email));
        return ResponseEntity.ok(data);
    }

}
