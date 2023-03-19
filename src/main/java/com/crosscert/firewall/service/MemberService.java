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
public class MemberService implements UserDetailsService {

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
    public void signup(MemberDTO.Request.Create memberDTO) {
        log.info("{}.signup",this.getClass());

        //중복 회원 검증
        if(isPresentMember(memberDTO.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        //중복 IP주소 검증
        if (ipService.isPresentIp(new IpAddress(memberDTO.getDevIp())) || ipService.isPresentIp(new IpAddress(memberDTO.getNetIp()))){
            throw new IllegalArgumentException("이미 존재하는 IP주소 입니다.");
        }

        //DTO -> Entity
        Member member = Member.builder()
                .name(memberDTO.getName())
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .role(memberDTO.getRole()).build();

        member.setDevIpByAddress(memberDTO.getDevIp(), memberDTO.getName());
        member.setNetIpByAddress(memberDTO.getNetIp(), memberDTO.getName());
        memberRepository.save(member);
    }

    //스프링시큐리티 로그인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("없는 회원 정보 입니다."));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().name()));
        return new User(member.getEmail(), member.getPassword(), authorities);
    }


    public void deleteByEmail(String email) {
        memberRepository.deleteByEmail(email);
    }
}




