package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.Ip;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.QIp;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Repository
public interface IPRepository extends JpaRepository<Ip, Long>, IPRepositoryCustom {

    Optional<Ip> findByAddress(IpAddress address);

    boolean existsByAddress(IpAddress address);
}
