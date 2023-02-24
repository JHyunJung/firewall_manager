package com.crosscert.firewall.service;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.Member;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService implements UserDetailsService {
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
        };

        //비밀번호 암호화
        String encPassword = passwordEncoder.encode(memberDTO.getPassword());

        //DTO -> Entity
        Member member = Member.builder()
                .name(memberDTO.getName())
                .email(memberDTO.getEmail())
                .password(encPassword)
                .role(memberDTO.getRole()).build();


        //가입시 개발망 IP정보, 인터넷망 IP정보가 있을 경우 함께 저장
        if(isNotEmptyIpAddress(memberDTO.getDevIp())){
            member.setDevIpByAddress(memberDTO.getDevIp());
        }
        if(isNotEmptyIpAddress(memberDTO.getNetIp())){
            member.setNetIpByAddress(memberDTO.getNetIp());
        }

        //DB 저장
        memberRepository.save(member);
    }

    public boolean isNotEmptyIpAddress(String ipAddress) {
        return ipAddress != null && !ipAddress.equals("");
    }


    //스프링시큐리티 로그인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("없는 회원 정보 입니다."));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().name()));
        return new User(member.getEmail(), member.getPassword(), authorities);
    }

    @Transactional
    public void deleteByEmail(String email) {
        log.info("{}.deleteByEmail",this.getClass());

        //유저 정보 갖고 오기
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("없는 회원 정보 입니다."));

        //DB삭제
        memberRepository.delete(member);
    }
}




