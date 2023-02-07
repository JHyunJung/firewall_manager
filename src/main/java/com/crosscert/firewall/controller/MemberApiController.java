package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.service.IPService;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final IPService ipService;

    @PutMapping("/member/{id}")
    public boolean editMember(@PathVariable("id") Long id, MemberDTO.Request.EditInfo memberDTO) {
        Member findMember = memberService.findMember(id);

        IP devIP = ipService.findWithAddress(memberDTO.getDevIp())
                .orElseGet(() -> ipService.create(findMember, memberDTO.getDevIp()));

        IP netIP = ipService.findWithAddress(memberDTO.getNetIp())
                .orElseGet(() -> ipService.create(findMember, memberDTO.getNetIp()));

        return memberService.editMember(findMember,memberDTO, devIP, netIP);
    }

}
