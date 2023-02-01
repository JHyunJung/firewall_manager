package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDto;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/firewall/get/member/all")
    @ResponseBody
    public List<MemberDto.FindAllMemberDto> getMemberList() {

        List<MemberDto.FindAllMemberDto> memberList = memberService.getMemberList();
        log.info(String.valueOf(memberList.size()));
        log.info(String.valueOf(memberList.get(0).getClass()));
        log.info(memberList.get(0).toString());

        return memberList;
    }
}
