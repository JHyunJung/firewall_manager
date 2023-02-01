package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.IP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPRepository extends JpaRepository<IP, Long> {

}
