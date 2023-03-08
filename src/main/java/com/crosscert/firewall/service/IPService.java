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

    @Transactional(readOnly = true)
    public IP findByAddress(IpAddress address) {
        return ipRepository.findByAddress(address)
                .orElseThrow(() -> new IllegalArgumentException("해당 IP가 존재하지 않습니다."));
    }

    public IP save (IP ip) {
        return ipRepository.save(ip);
    }

    @Transactional(readOnly = true)
    public IP findById(Long id) {
        return ipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 IP가 존재하지 않습니다."));
    }

    public void delete(Long id) {
        ipRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<IP> findAll() {
        return ipRepository.findAll();
    }
}
