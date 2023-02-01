package com.crosscert.firewall;

import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.Member;
import com.crosscert.firewall.entity.Role;
import com.crosscert.firewall.repository.IPRepository;
import com.crosscert.firewall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final IPRepository ipRepository;

    /**
     * 확인용 초기 데이터 추가
     * 스프링 컨테이너가 완전히 초기화를 끝내고 실행되는 이벤트임
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {

        IP ip = IP.builder()
                .domain("domain")
                .description("description")
                .build();
        ipRepository.save(ip);

        for (int i = 0; i < 20; i++) {
            Member member = Member.builder()
                    .name("name"+i)
                    .email("testData"+i+"@naver.com")
                    .password("123456"+i)
                    .role(Role.MEMBER)
                    .devIp(ip)
                    .netIp(ip)
                    .fireWallList(new ArrayList<>())
                    .build();

            memberRepository.save(member);
        }
        log.info("testData 완료");
    }
}
