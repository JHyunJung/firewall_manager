package com.crosscert.firewall;

import com.crosscert.firewall.repository.IPRepository;
import com.crosscert.firewall.repository.MemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FirewallApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirewallApplication.class, args);
	}

	@Bean //빈등록해야 이벤트리스너 발동
//	@Profile("local")  //스프링 프로파일이 local 일때만 testData 삽입
	public TestDataInit testDataInit(MemberRepository memberRepository, IPRepository ipRepository) {
		return new TestDataInit(memberRepository, ipRepository);
	}

}
