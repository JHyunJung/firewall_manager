package com.crosscert.firewall.controller;

import com.crosscert.firewall.service.FireWallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class FireWallController {


    private final FireWallService fireWallService;


    @GetMapping("/firewalls")
    public String fireWalls(){

//        Page<FireWall> fireWallPage = fireWallService.findAll(0, 10);
//        PageInfo pageInfo = new PageInfo(0, 10, (int) fireWallPage.getTotalElements(), fireWallPage.getTotalElements());
//
//        model.addAttribute("members", members);
        return "firewalls";
    }

}
