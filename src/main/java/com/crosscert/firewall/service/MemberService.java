package com.crosscert.firewall.service;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final IPService ipService;

    @Transactional(readOnly = true)
    public List<MemberDTO.Response.Public> findAll() {
        return memberRepository.findMemberFetchJoin().stream()
                .map(m -> new MemberDTO.Response.Public(
                        m.getId(),
                        m.getName(),
                        m.getEmail(),
                        m.getRole(),
                        m.getDevIp().getAddress().getAddress(),
                        m.getNetIp().getAddress().getAddress()))
                .collect(Collectors.toList());
    }

    public MemberDTO.Response.Public findMember(Long id) {
        Member m = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 Member 가 없습니다"));
        return new MemberDTO.Response.Public(
                m.getId(),
                m.getName(),
                m.getEmail(),
                m.getRole(),
                m.getDevIp().getAddress().getAddress(),
                m.getNetIp().getAddress().getAddress());
    }

    public boolean editMember(Long id, MemberDTO.Request.EditInfo memberDTO) {
        Optional<Member> findMember = memberRepository.findById(id);

        if (findMember.isEmpty()) {
            throw new RuntimeException("해당 Member 존재하지 않습니다");
        }

        IP devIP = ipService.findIpForEdit(findMember.get(),memberDTO.getDevIp());
        IP netIP = ipService.findIpForEdit(findMember.get(),memberDTO.getNetIp());

        Member member = findMember.get();
        member.editMember(memberDTO, devIP, netIP);

        return true;
    }
}


