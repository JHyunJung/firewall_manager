package com.crosscert.firewall.service;


import com.crosscert.firewall.entity.Log;
import com.crosscert.firewall.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public Log save(Log log) {
        return logRepository.save(log);
    }

    public Log findById(Long id){
        return logRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 아이디 입니다."));
    }

    public void delete(Long id){
        logRepository.deleteById(id);
    }

}
