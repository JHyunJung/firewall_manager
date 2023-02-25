package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpAddress;
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

    public Optional<IP> findByAddress(String address) {
        return ipRepository.findByAddress(createIpAddress(address));
    }

    public IP save (IP ip) {
        return ipRepository.save(ip);
    }

    public IP findById(Long id) {
        return ipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 아이디 입니다."));
    }

    public void delete(Long id) {
        ipRepository.deleteById(id);
    }

    public List<IP> findAll() {
        return ipRepository.findAll();
    }

    private IpAddress createIpAddress(String address) {
        IpAddress ipAddress = new IpAddress(address);
        return ipAddress;
    }

}
