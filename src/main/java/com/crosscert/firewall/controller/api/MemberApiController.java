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
        Member findMember = memberService.findById(id);

        Ip devIp;
        Ip netIp;

        //개발망IP
        if(memberDTO.getDevIp().equals(findMember.getDevIpValue())){
            devIp = findMember.getDevIp();
        }else {
            devIp = ipService.allocateIp(memberDTO.getDevIp(),findMember.getName()+" 개발망");
            findMember.editDevIpDescription(null);
        }
        //인터넷망IP
        if(memberDTO.getNetIp().equals(findMember.getNetIpValue())){
            netIp = findMember.getNetIp();
        }else {
            netIp = ipService.allocateIp(memberDTO.getNetIp(),findMember.getName()+" 인터넷망");
            findMember.editNetIpDescription(null);
        }

        memberService.edit(findMember, memberDTO.getRole(), devIp, netIp);
        MemberDTO.Response.Edit resultDto = convertToEditDto(findMember);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PutMapping("/member/resetPassword/{id}")
    public ResponseEntity<MemberDTO.Response.Edit> resetPassword(@PathVariable("id") Long id, @RequestBody MemberDTO.Request.ResetPassword memberDTO) {
        Member findMember = memberService.findById(id);
        memberService.editPassword(findMember,memberDTO.getNewPassword());
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
