package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository <Log, Long> {
}
