package com.crosscert.firewall.Impl;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.service.IPService;
import com.crosscert.firewall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl {

    private final MemberService memberService;
    private final IPService ipService;

    public boolean editMember(Long id, MemberDTO.Request.EditInfo memberDTO) {
        Member findMember = memberService.findMember(id);

        Optional<IP> dev = ipService.findWithAddress(memberDTO.getDevIp());
        Optional<IP> net = ipService.findWithAddress(memberDTO.getNetIp());

        IP devIP;
        IP netIP;
        if (dev.isEmpty()) {
            devIP = ipService.create(findMember, memberDTO.getDevIp());
        } else {
            devIP = dev.get();
        }

        if (net.isEmpty()) {
            netIP = ipService.create(findMember, memberDTO.getNetIp());
        } else {
            netIP = net.get();
        }

        return memberService.editMember(findMember,memberDTO, devIP, netIP);
    }
}
