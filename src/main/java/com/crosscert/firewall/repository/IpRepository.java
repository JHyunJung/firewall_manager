package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IpRepository extends JpaRepository<Ip, Long>, IpRepositoryCustom {

    Optional<Ip> findByAddress(IpAddress address);

    boolean existsByAddress(IpAddress address);
}
