package com.crosscert.firewall.service;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.dto.ResDTO;
import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.entity.ResultType;
import com.crosscert.firewall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findMemberFetchJoin();
    }

    public List<MemberDTO.Response.Public> changeResDtos(List<Member> memberList) {
        return memberList.stream()
                .map(m -> new MemberDTO.Response.Public(
                        m.getId(),
                        m.getName(),
                        m.getEmail(),
                        m.getRole(),
                        m.getDevIp().getAddress().getAddress(),
                        m.getNetIp().getAddress().getAddress()))
                .collect(Collectors.toList());
    }


    public Member findMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 Member 가 없습니다"));
    }

    public MemberDTO.Response.Public changeResDto(Member m) {
        return new MemberDTO.Response.Public(
                m.getId(),
                m.getName(),
                m.getEmail(),
                m.getRole(),
                m.getDevIp().getAddress().getAddress(),
                m.getNetIp().getAddress().getAddress());
    }

    public ResDTO.Public edit(Member member, MemberDTO.Request.EditInfo memberDTO, IP devIP, IP netIP) {
        member.edit(memberDTO, devIP, netIP);
        return new ResDTO.Public(ResultType.OK);
    }


}


