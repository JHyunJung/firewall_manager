package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/myinfo")
public class AccountController {

    private final MemberService memberService;

    //내 IP변경 페이지
    @GetMapping("/ip")
    public String myIpPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        Member findMember = memberService.findByEmail(userDetails.getUsername());
        MemberDTO.Response.Public memberDto = convertToPublicDto(findMember);
        model.addAttribute("member", memberDto);
        return "myInfoIp";
    }

    //비밀번호 변경 페이지
    @GetMapping("/password")
    public String myPasswordPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        Member findMember = memberService.findByEmail(userDetails.getUsername());
        MemberDTO.Response.Public memberDto = convertToPublicDto(findMember);
        model.addAttribute("member", memberDto);
        return "myInfoPassword";
    }

    private MemberDTO.Response.Public convertToPublicDto(Member member){
        return new MemberDTO.Response.Public(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getRole(),
                member.getDevIpValue(),
                member.getNetIpValue()
        );
    }


}
