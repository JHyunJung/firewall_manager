package com.crosscert.firewall;

import com.crosscert.firewall.testgit.PushTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableJpaAuditing
@SpringBootApplication
public class FirewallApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirewallApplication.class, args);
	}

	//개발용 인메모리 사용자
	@Bean
	public UserDetailsService users(){
		UserDetails user = User.builder()
				.username("ycshin")
				.password("{noop}asdasd")
				.roles("USER")
				.build();

		UserDetails admin = User.builder()
				.username("admin")
				.password("{noop}asdasd")
				.roles("USER","ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user,admin);
	}

}
