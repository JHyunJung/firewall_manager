package com.crosscert.firewall.service;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

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

    public boolean isPresentMember(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public void signup(MemberDTO.Request.Create memberDTO) {
        log.info("{}.signup",this.getClass());

        //중복 회원 검증
        if(isPresentMember(memberDTO.getEmail())){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        //비밀번호 암호화
        String encPassword = passwordEncoder.encode(memberDTO.getPassword());

        //DTO -> Entity
        Member member = Member.builder()
                .name(memberDTO.getName())
                .email(memberDTO.getEmail())
                .password(encPassword)
                .role(memberDTO.getRole()).build();

        //개발망 IP정보
        if(memberDTO.getDevIp()!=null && !memberDTO.getDevIp().equals("")){
            log.info("devIp : {}",memberDTO.getDevIp());
            member.setDevIpByAddress(memberDTO.getDevIp());
        }

        //인터넷망 IP정보
        if(memberDTO.getNetIp()!=null && !memberDTO.getNetIp().equals("")){
            log.info("devIp : {}",memberDTO.getNetIp());
            member.setNetIpByAddress(memberDTO.getNetIp());
        }

        //DB 저장
        memberRepository.save(member);
    }
}


