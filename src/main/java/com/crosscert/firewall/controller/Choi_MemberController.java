package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDto;
import com.crosscert.firewall.service.Choi_MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class Choi_MemberController {

    private final Choi_MemberService memberService;

    @GetMapping("/firewall/member/findall")
    @ResponseBody
    public List<MemberDto.FindAllMemberDto> getMemberList() {
        return memberService.getMemberList();
    }
}
