package com.crosscert.firewall.service;

import com.crosscert.firewall.annotation.LogTrace;
import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.entity.Role;
import com.crosscert.firewall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final IpService ipService;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<Member> findAllFetch() {
        return memberRepository.findMemberFetchJoin();
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 Member가 없습니다."));
    }

    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 Member가 없습니다."));
    }

    public void edit(Member member, Role role, Ip devIp, Ip netIp) {
        member.edit(role, devIp, netIp);
    }
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @LogTrace
    public boolean isPresentMember(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public boolean signup(MemberDTO.Request.Create memberDTO) {
        log.info("{}.signup",this.getClass());

        //MEMBER로만 가입되도록 백단 보안 처리
        if(memberDTO.getRole()!=Role.MEMBER){
            log.error("강제 LEADER 가입 시도 발생");
            throw new IllegalArgumentException("해당 권한은 선택할 수 없습니다.");
        }

        //중복 회원 검증
        if(isPresentMember(memberDTO.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        //DTO -> Entity
        Member member = Member.builder()
                .name(memberDTO.getName())
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .role(memberDTO.getRole()).build();

//        member.setDevIpByAddress(memberDTO.getDevIp(), memberDTO.getName());
//        member.setNetIpByAddress(memberDTO.getNetIp(), memberDTO.getName());
        memberRepository.save(member);

        return true;
    }

    public void deleteByEmail(String email) {
        memberRepository.deleteByEmail(email);
    }

    @Transactional
    public void editMyIp(Member member, Ip devIp, Ip netIp) {
        member.editIp(devIp,netIp);
    }

    @Transactional
    public void editMyPw(Member member, String newPassword) {
        member.editPw(passwordEncoder.encode(newPassword));
    }
}




