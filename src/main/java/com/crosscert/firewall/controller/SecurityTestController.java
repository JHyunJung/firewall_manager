package com.crosscert.firewall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/sec")
public class SecurityTestController {

    @GetMapping("")
    public String index(Model model, Principal principal){
        if(principal != null){
            model.addAttribute("message","현재 로그인 된 분 : "+principal.getName());
        }else {
            model.addAttribute("message","비 로그인 상태입니다.");
        }
        return "/securitytest/index";
    }

    @GetMapping("/member/test")
    public String memberTestpage(Model model, Principal principal){
        model.addAttribute("message","현재 로그인 된 분 : "+principal.getName());
        return "/securitytest/membertestpage";
    }

    @GetMapping("/leader/test")
    public String leaderTestpage(Model model, Principal principal){
        model.addAttribute("message","현재 로그인 된 분 : "+principal.getName());
        return "/securitytest/leadertestpage";
    }

}
