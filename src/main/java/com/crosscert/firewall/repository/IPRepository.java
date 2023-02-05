package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.IP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPRepository extends JpaRepository<IP, Long> {

}
