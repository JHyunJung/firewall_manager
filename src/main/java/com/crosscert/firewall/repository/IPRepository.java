package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPRepository extends JpaRepository<Ip, Long> {

    Optional<Ip> findByAddress(IpAddress address);

    boolean existsByAddress(IpAddress address);
}
