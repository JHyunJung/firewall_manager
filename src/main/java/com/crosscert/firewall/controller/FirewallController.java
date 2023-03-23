package com.crosscert.firewall.controller;


import com.crosscert.firewall.service.FirewallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/firewall")
public class FirewallController {

    @Autowired
    FirewallService firewallService;


}