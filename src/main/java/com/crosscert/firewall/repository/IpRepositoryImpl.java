package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.crosscert.firewall.entity.QIp.ip;

public class IpRepositoryImpl implements IpRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public IpRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<IpAddress> findAllWithoutMember() {

        QMember devMember = QMember.member;
        QMember netMember = new QMember("newMember");

        return queryFactory.select(ip.address).from(ip)
                .leftJoin(devMember).on(ip.eq(devMember.devIp))
                .leftJoin(netMember).on(ip.eq(netMember.netIp))
                .where(devMember.isNull().and(netMember.isNull()))
                .orderBy(ip.address.address.asc())
                .fetch();
    }
}
