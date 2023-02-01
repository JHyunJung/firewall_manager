package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.FireWall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireWallRepository extends JpaRepository<FireWall,Long> {

}
