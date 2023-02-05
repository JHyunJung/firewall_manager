package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.repository.IPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class IPService {

    private final IPRepository ipRepository;

    /**
     * 해당 IP가 없으면 생성 후 리턴
     * 해당 IP가 있으면 해당 IP에 address가 요청 address와 맞는지 확인 후 변경
     */
    public IP findIpForEdit(Member member, String address) {
        IpAddress ipAddress = new IpAddress(address);
        Optional<IP> ip = ipRepository.findByAddress(ipAddress);
        if (ip.isEmpty()) {
            return createIpForEdit(member, address);
        }

        IP findIp = ip.get();
        if (!findIp.getAddress().getAddress().equals(address)) {
            findIp.getAddress().editIpAddress(address);
        }

        return findIp;
    }

    private IP createIpForEdit(Member member, String address) {
        IpAddress ipAddress = new IpAddress(address);
        IP ip = new IP(ipAddress, "domain", "description", member, member);
        return ipRepository.save(ip);
    }
}
