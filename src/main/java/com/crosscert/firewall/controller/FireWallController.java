package com.crosscert.firewall.controller;


import com.crosscert.firewall.dto.FireWallDTO;
import com.crosscert.firewall.entity.FireWall;
import com.crosscert.firewall.service.FireWallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class FireWallController {

    private final FireWallService fireWallService;


    @GetMapping("/firewalls")
    public String fireWall(Model model){
        List<FireWallDTO.Response.Public> firewalls = fireWallService.findAll()
                .stream()
                .map(this::convertToPublicDto)
                .collect(Collectors.toList());

        model.addAttribute("firewalls", firewalls);

        return "firewalls";
    }

    @GetMapping("/firewall")
    public String create(Model model) {

        return "newFirewall";
    }

    private FireWallDTO.Response.Public convertToPublicDto(FireWall fireWall){
        return FireWallDTO.Response.Public
                .builder()
                .id(fireWall.getId())
                .start(fireWall.getStart().getAddressValue())
                .destination(fireWall.getDestination())
                .memberName(fireWall.getMember().getName())
                .description(fireWall.getDescription())
                .endDate(fireWall.getEndDate())
                .ended(fireWall.getIsEnded())
                .build();
    }

}