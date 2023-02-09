package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.repository.IPRepository;
import com.crosscert.firewall.repository.MemberRepository;
import com.crosscert.firewall.service.IPService;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final IPService ipService;
    private final MemberRepository memberRepository;
    private final IPRepository ipRepository;

    @GetMapping("/members")
    public String members(Model model) {
        List<Member> memberList = memberService.findAll();
        List<MemberDTO.Response.Public> members = memberService.changeResDtos(memberList);

        model.addAttribute("members", members);
        return "members";
    }

    @GetMapping("/member/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        Member member = memberService.findMember(id);
        MemberDTO.Response.Public memberDto = memberService.changeResDto(member);

        List<IP> ipList = ipService.findAll();
        List<String> addresses = ipService.getAddresses(ipList);
        System.out.println(addresses.size());

        model.addAttribute("member", memberDto);
        model.addAttribute("addresses", addresses);
        return "memberEdit";
    }
}
