package com.example.umcmatchingcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UmcmatchingcenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UmcmatchingcenterApplication.class, args);
	}

}
