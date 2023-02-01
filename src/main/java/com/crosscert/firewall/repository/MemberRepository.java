package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select m from Member m left join fetch m.devIp")
    List<Member> findMemberFetchJoin();
}
