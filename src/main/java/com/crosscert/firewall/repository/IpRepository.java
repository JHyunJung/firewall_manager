package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IpRepository extends JpaRepository<Ip, Long> {

    Optional<Ip> findByAddress(IpAddress address);

    boolean existsByAddress(IpAddress address);

    @Query("SELECT ip FROM Ip ip LEFT JOIN FETCH ip.devMember LEFT JOIN FETCH ip.netMember WHERE ip.devMember.devIp IS NULL AND ip.netMember.netIp IS NULL ORDER BY ip.address.address")
    List<Ip> findAllWithoutMember();
}
