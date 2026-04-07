package com.sadi.FinanceManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling     // to run the mail via notification
@SpringBootApplication
public class FinanceManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceManagerApplication.class, args);
	}

}
