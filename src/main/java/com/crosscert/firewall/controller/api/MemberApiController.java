package com.crosscert.firewall.controller.api;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.service.IpService;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;
    private final IpService ipService;

    //초기화 비밀번호 값
    private final String RESET_PASSWORD = "cross12#$";

    @GetMapping("/member/list") // 현재 미사용 API
    public ResponseEntity<List<MemberDTO.Response.Public>> findAll(){

        List<Member> memberList = memberService.findAllFetch();
        List<MemberDTO.Response.Public> resultList = memberList.stream()
                .map(this::convertToPublicDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


    @PutMapping("/member/{id}")
    public ResponseEntity<MemberDTO.Response.Edit> edit(@PathVariable("id") Long id, @RequestBody MemberDTO.Request.Edit memberDTO) {
        //수정하려는 계정의 정보
        Member findMember = memberService.findById(id);
        Ip devIp = ipService.assignMemberDevIp(findMember,memberDTO.getDevIp());
        Ip netIp = ipService.assignMemberNetIp(findMember,memberDTO.getNetIp());

        memberService.edit(findMember, memberDTO.getRole(), devIp, netIp);
        MemberDTO.Response.Edit resultDto = convertToEditDto(findMember);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PutMapping("/member/resetPassword/{id}")
    public ResponseEntity<MemberDTO.Response.Edit> resetPassword(@PathVariable("id") Long id) {
        Member findMember = memberService.findById(id);
        memberService.editPassword(findMember, RESET_PASSWORD);
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
                member.getName(),
                member.getRole(),
                member.getDevIpValue(),
                member.getNetIpValue()
        );
    }

}
