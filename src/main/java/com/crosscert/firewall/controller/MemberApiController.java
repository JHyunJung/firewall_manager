package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDto;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/members")
    @ResponseBody
    public List<MemberDto.ResFindAll> findMemberList() {

        List<MemberDto.ResFindAll> memberList = memberService.findMemberList();
        return memberList;
    }
}
