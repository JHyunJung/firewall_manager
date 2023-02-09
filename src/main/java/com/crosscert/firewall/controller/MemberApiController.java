package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.dto.ResDTO;
import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.exception.CustomException;
import com.crosscert.firewall.exception.ErrorCode;
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
    public ResDTO.Public editMember(@PathVariable("id") Long id, MemberDTO.Request.EditInfo memberDTO) {
        Member findMember = memberService.findMember(id);

        IP devIP = ipService.findWithAddress(memberDTO.getDevIp())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_IP));

        IP netIP = ipService.findWithAddress(memberDTO.getNetIp())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_IP));

        return memberService.editMember(findMember,memberDTO, devIP, netIP);
    }

}
