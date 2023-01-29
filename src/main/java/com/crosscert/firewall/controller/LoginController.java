package com.crosscert.firewall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("message","메인페이지입니다.");
        return "/index";
    }

}
