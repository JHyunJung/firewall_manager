package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final Environment environment;

//    @Value("${spring.datasource.url}")
//    private String java;


    @GetMapping("/members")
    public String getMemberPage(Model model) {
        List<MemberDTO.Response.Public> members = memberService.findAll();
        model.addAttribute("members",members);
        return "members";
    }

    @GetMapping("/test12")
    @ResponseBody
    public String test(){
        return "test";
    }
}
