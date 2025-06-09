package com.moneyly.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan("com.moneyly")
public class MoneylyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneylyApplication.class, args);
	}

}