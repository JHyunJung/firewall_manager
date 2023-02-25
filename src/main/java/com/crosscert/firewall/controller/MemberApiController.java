package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.service.IPService;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final IPService ipService;

    @PutMapping("/member/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") Long id, MemberDTO.Request.EditInfo memberDTO) {
        Member findMember = memberService.findById(id);

        IP devIP = ipService.findByAddress(memberDTO.getDevIp())
                .orElseThrow(() -> new NullPointerException("해당 IP가 존재하지 않습니다"));

        IP netIP = ipService.findByAddress(memberDTO.getNetIp())
                .orElseThrow(() -> new NullPointerException("해당 IP가 존재하지 않습니다"));

        memberService.edit(findMember,memberDTO, devIP, netIP);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
