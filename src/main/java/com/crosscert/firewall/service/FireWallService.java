package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.FireWall;
import com.crosscert.firewall.repository.FireWallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FireWallService {

    private final FireWallRepository fireWallRepository;

    public FireWall save(FireWall fireWall) {
        return fireWallRepository.save(fireWall);
    }

    public FireWall findById(Long id){
        return fireWallRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 방확벽이 존재하지 않습니다."));
    }

    public List<FireWall> findAll(){
        return fireWallRepository.findAll();
    }

    public void delete(Long id){
        fireWallRepository.deleteById(id);
    }

    public Page<FireWall> findAll(PageRequest pageRequest) {
        return fireWallRepository.findAll(pageRequest);
    }
}
