package com.crosscert.firewall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("hello","서버에서 온 값");
        return "/index";
    }

}
