package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;

import java.util.List;

public interface IPRepositoryCustom {
    List<IpAddress> findAllWithNoMember();
}
