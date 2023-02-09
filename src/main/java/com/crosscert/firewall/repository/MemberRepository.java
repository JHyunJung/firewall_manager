package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select m from Member m left join fetch m.devIp")
    List<Member> findMemberFetchJoin();

    boolean existsByEmail(String email);
}
