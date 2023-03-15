package com.crosscert.firewall.repository;

import com.crosscert.firewall.entity.FireWall;
import com.crosscert.firewall.entity.QFireWall;
import com.crosscert.firewall.entity.search.SearchFirewall;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface FirewallRepository extends JpaRepository<FireWall, Long>, QuerydslPredicateExecutor<FireWall> {

    default BooleanBuilder search(SearchFirewall search) {


        QFireWall qFireWall;
        qFireWall = QFireWall.fireWall;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        return booleanBuilder;
    }

    default List<FireWall> searchList(SearchFirewall search, Sort sort){

        BooleanBuilder booleanBuilder = search(search);
        return StreamSupport.stream(findAll(booleanBuilder, sort).spliterator(), false).collect(Collectors.toList());
    }

    default Page<FireWall> searchPage(SearchFirewall search, Pageable pageable) {

        BooleanBuilder booleanBuilder = search(search);
        return findAll(booleanBuilder, pageable);
    }

}
