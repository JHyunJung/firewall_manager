package com.crosscert.firewall.service;

import com.crosscert.firewall.dto.MemberDto;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class Choi_MemberService {

    private final MemberRepository memberRepository;

    public List<MemberDto.FindAllMemberDto> getMemberList() {
        List<Member> memberList = memberRepository.findAll();
        return changeDto(memberList);
    }

    private List<MemberDto.FindAllMemberDto> changeDto(List<Member> memberList) {
        return memberList.stream().
                        map(MemberDto.FindAllMemberDto::new).
                        collect(Collectors.toList());
    }
}


