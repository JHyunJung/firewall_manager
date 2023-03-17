package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.IpAddress;

import java.util.List;

public interface IpRepositoryCustom {
    List<IpAddress> findAllWithoutMember();
}
