package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.repository.IpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IpService {

    private final IpRepository ipRepository;

    @Transactional(readOnly = true)
    public Ip findByAddress(IpAddress address) {
        return ipRepository.findByAddress(address)
                .orElseThrow(() -> new IllegalArgumentException("해당 IP가 존재하지 않습니다."));
    }

    public Ip save (Ip ip) {
        return ipRepository.save(ip);
    }

    @Transactional(readOnly = true)
    public Ip findById(Long id) {
        return ipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 IP가 존재하지 않습니다."));
    }

    public void delete(Long id) {
        ipRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Ip> findAllWithoutMember() {
        return ipRepository.findAllWithoutMember();
    }

    public boolean isPresentIp(IpAddress address) {
        return ipRepository.existsByAddress(address);
    }
}
