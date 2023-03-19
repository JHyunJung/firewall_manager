package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.FireWall;
import com.crosscert.firewall.repository.FireWallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class FireWallService {

    private final FireWallRepository firewallRepository;

    public FireWall save(FireWall firewall) {
        return firewallRepository.save(firewall);
    }

    public FireWall findById(Long id){
        return firewallRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 방확벽이 존재하지 않습니다."));
    }

    public void delete(Long id){
        firewallRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<FireWall> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createDate").descending());
        return firewallRepository.findAllByOrderByCreateDateDesc(pageRequest);
    }


//    @ApiOperation(value = "firewall 상세 조회")
//    @Transactional
//    public FireWall detailFireWall(Long id) {
//        return firewallRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));
//    }
//
//    /**
//     * firewall 등록
//     */
//    @ApiOperation(value = "firewall 등록")
//    @Transactional
//    public FireWall saveFireWall(FireWall fireWall) {
//        return firewallRepository.save(fireWall);
//    }
//
//    /**
//     * firewall 수정
//     */
//    @ApiOperation(value = "firewall 수정")
//    @Transactional
//    public FireWall updateFireWall(FireWall fireWall) {
//        FireWall vo = firewallRepository.getReferenceById(fireWall.getId());
//        if(fireWall.getPort() != null){
//            fireWall.setPort(vo.getPort());
//        };
//        if (vo.getEndDate() != null) {
//
//            fireWall.getEndDate();
//        }
//
//        return firewallRepository.save(fireWall);
//    }
//
//
//    /**
//     * firewall 삭제
//     */
//    @ApiOperation(value = "firewall 삭제")
//    @Transactional
//    public FireWall deleteFireWall(String id) {
//        FireWall basic = firewallRepository.findById(Long.valueOf(id)).orElseThrow();
//        basic.setEnded(false);
//        return firewallRepository.save(basic);
//    }

}



