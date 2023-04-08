package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.service.IpService;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
public class LoginController {

    private final MemberService memberService;
    private final IpService ipService;

    //로그인 페이지
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    //회원가입 페이지
    @GetMapping("/signup")
    public String signUpPage(){
        return "signup";
    }


    //회원가입 진행
    @PostMapping("/signup")
    public String signup(MemberDTO.Request.Create memberDTO, Model model){
        log.info("{}.signup",this.getClass());
        boolean result = memberService.signup(memberDTO);
        model.addAttribute("signupResult", result);
        return "login";
    }


    //이메일 중복체크
    @GetMapping("/signup/checkDuplicateEmail")
    @ResponseBody
    public ResponseEntity<Object> checkDuplicateEmail(@RequestParam("email") String email) {
        log.info("{}.checkDuplicateEmail",this.getClass());
        Map<String, Object> data = new HashMap<>();
        data.put("result", memberService.isPresentMember(email));
        return ResponseEntity.ok(data);
    }

    //IP주소 중복체크
    @GetMapping("/signup/checkDuplicateIpAddress")
    @ResponseBody
    public ResponseEntity<Object> checkDuplicateIpAddress(@RequestParam("ipAddress") String ipAddress) {
        log.info("{}.checkDuplicateIpAddress",this.getClass());
        Map<String, Object> data = new HashMap<>();
        data.put("result", ipService.isPresentIp(new IpAddress(ipAddress)));
        return ResponseEntity.ok(data);
    }

}
