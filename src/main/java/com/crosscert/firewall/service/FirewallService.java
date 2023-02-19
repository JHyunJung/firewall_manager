package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.FireWall;
import com.crosscert.firewall.entity.search.SearchFirewall;
import com.crosscert.firewall.repository.FirewallRepository;
import com.querydsl.core.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FirewallService {
    @Autowired
    FirewallRepository firewallRepository;
    /**
     * firewall 목록
     */
    @ApiOperation(value = "firewall 목록")
    @Transactional(readOnly = true)
    public Page<FireWall> searchPage(SearchFirewall search, Pageable page) {
        return firewallRepository.searchPage(search, page);
    }

    /**
     * firewall 상세 조회
     */
    @ApiOperation(value = "firewall 상세 조회")
    @Transactional
    public FireWall detailFireWall(Long id) {
        return firewallRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));
    }

    /**
     * firewall 등록
     */
    @ApiOperation(value = "firewall 등록")
    @Transactional
    public FireWall saveFireWall(FireWall fireWall) {
        return firewallRepository.save(fireWall);
    }

    /**
     * firewall 수정
     */
    @ApiOperation(value = "firewall 수정")
    @Transactional
    public FireWall updateFireWall(FireWall fireWall) {
        FireWall vo = firewallRepository.getReferenceById(fireWall.getId());
        if(fireWall.getPort() != null){
            fireWall.setPort(vo.getPort());
        };
        if (vo.getEndDate() != null) {

            fireWall.getEndDate();
        }

        return firewallRepository.save(fireWall);
    }


    /**
     * firewall 삭제
     */
    @ApiOperation(value = "firewall 삭제")
    @Transactional
    public FireWall deleteFireWall(String id) {
        FireWall basic = firewallRepository.findById(Long.valueOf(id)).orElseThrow();
        basic.setEnded(false);
        return firewallRepository.save(basic);
    }

}
