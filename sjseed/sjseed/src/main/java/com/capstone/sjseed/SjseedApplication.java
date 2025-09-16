package com.capstone.sjseed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SjseedApplication {

	public static void main(String[] args) {
		SpringApplication.run(SjseedApplication.class, args);
	}

}
