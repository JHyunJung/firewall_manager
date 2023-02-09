package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.repository.IPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IPService {

    private final IPRepository ipRepository;

    public Optional<IP> findWithAddress(String address) {
        IpAddress ipAddress = new IpAddress(address);
        return ipRepository.findByAddress(ipAddress);
    }

    public IP create(Member member, String address) {
        IpAddress ipAddress = new IpAddress(address);
        IP ip = new IP(ipAddress, "domain", "description", member, member);
        return ipRepository.save(ip);
    }

    public List<IP> findAll() {
        return ipRepository.findAll();
    }

    public List<String> getAddresses(List<IP> ipList) {
        return ipList.stream().map(ip -> ip.getAddress().getAddress()).collect(Collectors.toList());
    }

}
