package com.cts.thundercars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
public class ThundercarsApplication {
	public static void main(String[] args) {
		log.info("START MAIN");
		SpringApplication.run(ThundercarsApplication.class, args);
		log.info("END MAIN");
	}
}
