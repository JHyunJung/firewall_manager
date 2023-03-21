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


@RequiredArgsConstructor
@Controller
public class FireWallController {


    private final FireWallService fireWallService;


    @GetMapping("/firewalls")
    public String fireWalls(Model model) {

        List<FireWallDTO.Response.Public> list = fireWallService.findAll()
                .stream()
                .map(this::convertToPublicDto)
                .collect(Collectors.toList());


        model.addAttribute("firewalls", list);

        return "firewalls";
    }


    public FireWallDTO.Response.Public convertToPublicDto(FireWall fireWall) {
        return new FireWallDTO.Response.Public(
                fireWall.getId(),
                fireWall.getStart().getAddressValue(),
                fireWall.getMember().getName(),
                fireWall.getEndDate().toString(),
                fireWall.getIsEnded()
        );
    }
}
