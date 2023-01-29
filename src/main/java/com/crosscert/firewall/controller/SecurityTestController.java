package com.crosscert.firewall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sec")
public class SecurityTestController {

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("message","스프링 시큐리티 메인페이지입니다.");
        return "/securitytest/index";
    }

    @GetMapping("/user/testpage")
    public String userTestpage(Model model){
        model.addAttribute("message","로그인이 필요한 페이지 입니다.");
        return "/securitytest/usertestpage";
    }

    @GetMapping("/testpage")
    public String testpage(Model model){
        model.addAttribute("message","로그인 안해도 들어올수있는 페이지 입니다.");
        return "/securitytest/testpage";
    }

}
