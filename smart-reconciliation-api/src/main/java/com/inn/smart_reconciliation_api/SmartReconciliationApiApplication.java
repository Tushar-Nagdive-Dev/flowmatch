package com.inn.smart_reconciliation_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SmartReconciliationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartReconciliationApiApplication.class, args);
	}

}
