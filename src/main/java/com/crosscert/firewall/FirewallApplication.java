package com.crosscert.firewall;

import com.crosscert.firewall.testgit.PushTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirewallApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirewallApplication.class, args);
		PushTest.pushTest();
	}

}
