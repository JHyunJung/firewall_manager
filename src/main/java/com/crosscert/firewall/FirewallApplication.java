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
}
