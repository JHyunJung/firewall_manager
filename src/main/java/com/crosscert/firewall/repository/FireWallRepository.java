package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.FireWall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireWallRepository extends JpaRepository<FireWall, Long>{

//    default List<FireWall> searchList(SearchFirewall search, Sort sort){
//
//        BooleanBuilder booleanBuilder = search(search);
//        return StreamSupport.stream(findAll(booleanBuilder, sort).spliterator(), false).collect(Collectors.toList());
//    }
//
//    default BooleanBuilder search(SearchFirewall search) {
//
//
//        QFireWall qFireWall;
//        qFireWall = QFireWall.fireWall;
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//        return booleanBuilder;
//    }
//
//    default Page<FireWall> searchPage(SearchFirewall search, Pageable pageable) {
//
//        BooleanBuilder booleanBuilder = search(search);
//        return findAll(booleanBuilder, pageable);
//    }

}
