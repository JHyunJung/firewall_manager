package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IPRepository extends JpaRepository<IP, Long> {

    Optional<IP> findByAddress(IpAddress address);

}
