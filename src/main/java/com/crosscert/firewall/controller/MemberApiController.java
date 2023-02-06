package com.crosscert.firewall.controller;

import com.crosscert.firewall.Impl.MemberServiceImpl;
import com.crosscert.firewall.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberServiceImpl memberService;

    @PutMapping("/member/{id}")
    public boolean editMember(@PathVariable("id") Long id, MemberDTO.Request.EditInfo memberDTO) {
        return memberService.editMember(id, memberDTO);
    }

}
