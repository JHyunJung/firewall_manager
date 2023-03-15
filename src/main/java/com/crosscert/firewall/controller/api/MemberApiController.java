package com.crosscert.firewall.controller.api;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.service.IPService;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final IPService ipService;


    @GetMapping("/api/members")
    public ResponseEntity<List<MemberDTO.Response.Public>> findAll(){

        List<Member> memberList = memberService.findAllFetch();
        List<MemberDTO.Response.Public> resultList = memberList.stream()
                .map(this::convertToPublicDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


    @PutMapping("/api/member/{id}")
    public ResponseEntity<MemberDTO.Response.Edit> edit(@PathVariable("id") Long id, @RequestBody MemberDTO.Request.Edit memberDTO) {
        Member findMember = memberService.findById(id);

        Ip devIp = ipService.findByAddress(new IpAddress(memberDTO.getDevIp()));
        Ip netIp = ipService.findByAddress(new IpAddress(memberDTO.getNetIp()));

        memberService.edit(findMember, memberDTO.getRole(), devIp, netIp);
        MemberDTO.Response.Edit resultDto = convertToEditDto(findMember);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    private MemberDTO.Response.Public convertToPublicDto(Member member){
        return new MemberDTO.Response.Public(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getRole(),
                member.getDevIpValue(),
                member.getNetIpValue()
        );
    }

    private MemberDTO.Response.Edit convertToEditDto(Member member){
        return new MemberDTO.Response.Edit(
                member.getId(),
                member.getRole(),
                member.getDevIpValue(),
                member.getNetIpValue()
        );
    }

}
