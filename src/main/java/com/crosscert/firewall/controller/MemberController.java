package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.service.IpService;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final IpService ipService;

    @GetMapping("/members")
    public String members(Model model) {
        List<MemberDTO.Response.Public> members = memberService.findAllFetch()
                .stream()
                .map(this::convertToPublicDto)
                .collect(Collectors.toList());

        model.addAttribute("members", members);
        return "members";
    }

    @GetMapping("/member/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        Member member = memberService.findById(id);
        MemberDTO.Response.Public memberDto = convertToPublicDto(member);

        List<Ip> ipList = ipService.findAllWithoutMember();
        List<String> addresses = ipList.stream()
                .map(Ip::getAddressValue)
                .collect(Collectors.toList());

        model.addAttribute("member", memberDto);
        model.addAttribute("addresses", addresses);
        return "memberEdit";
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
