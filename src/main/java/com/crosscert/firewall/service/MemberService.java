package com.crosscert.firewall.service;

import com.crosscert.firewall.dto.MemberDto;
import com.crosscert.firewall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<MemberDto.ResFindAll> findMemberList() {
        return memberRepository.findMemberFetchJoin().stream().
                map(MemberDto.ResFindAll::new).collect(Collectors.toList());
    }
}


