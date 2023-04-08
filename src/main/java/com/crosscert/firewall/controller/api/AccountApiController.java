package com.crosscert.firewall.controller.api;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.service.IpService;
import com.crosscert.firewall.service.LoginService;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountApiController {

    private final MemberService memberService;
    private final IpService ipService;

    private final LoginService loginService;

    @PutMapping("/myinfo/password")
    public ResponseEntity<MemberDTO.Response.Edit> editMyPassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MemberDTO.Request.EditPassword memberDTO) {
        Member findMember = memberService.findByEmail(userDetails.getUsername());
        String currentPassword = memberDTO.getCurrentPassword();
        String newPassword = memberDTO.getNewPassword();

        if (!loginService.authenticate(userDetails.getUsername(), currentPassword)) {
            //현재 비밀번호 미일치
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        //비밀번호 변경
        memberService.editPassword(findMember,newPassword);
        MemberDTO.Response.Edit resultDto = convertToEditDto(findMember);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }


    @PutMapping("/myinfo/ip")
    public ResponseEntity<MemberDTO.Response.Edit> editMyIp(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MemberDTO.Request.EditMyIp memberDTO) {
        //현재 접속 계정의 정보
        Member findMember = memberService.findByEmail(userDetails.getUsername());
        Ip devIp = ipService.assignMemberDevIp(findMember,memberDTO.getDevIp());
        Ip netIp = ipService.assignMemberNetIp(findMember,memberDTO.getNetIp());

        memberService.editIp(findMember,devIp,netIp);

        MemberDTO.Response.Edit resultDto = convertToEditDto(findMember);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
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
