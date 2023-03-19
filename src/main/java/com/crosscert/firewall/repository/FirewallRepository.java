package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.FireWall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireWallRepository extends JpaRepository<FireWall, Long>{
    Page<FireWall> findAllByOrderByCreateDateDesc(Pageable pageable);
    Page<FireWall> findAll(Pageable pageable);

//    public List<FireWall> searchList(SearchFirewall search, Sort sort){
//
//        BooleanBuilder booleanBuilder = search(search);
//        return StreamSupport.stream(findAll(booleanBuilder, sort).spliterator(), false).collect(Collectors.toList());
//    }
//
//    public Page<FireWall> searchPage(SearchFirewall search, Pageable pageable) {
//
//        BooleanBuilder booleanBuilder = search(search);
//        return findAll(booleanBuilder, pageable);
//    }

}
