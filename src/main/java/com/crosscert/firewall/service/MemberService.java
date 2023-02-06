package com.crosscert.firewall.service;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

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

    private void isPresentMember(String email) {
        memberRepository.findByEmail(email).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    @Transactional
    public String signup(MemberDTO.Request.Create memberDTO) {

        //중복 회원 검증
        isPresentMember(memberDTO.getEmail());

        //TODO : IP 정보 저장

        //DTO -> Entity
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Member member = Member.builder()
                .name(memberDTO.getName())
                .email(memberDTO.getEmail())
                .password(encoder.encode(memberDTO.getPassword()))  //비밀번호 암호화
                .role(memberDTO.getRole()).build();

        //DB 저장
        Member savedMember = memberRepository.save(member);

        return savedMember.getEmail();
    }
}


