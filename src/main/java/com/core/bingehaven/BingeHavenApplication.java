package com.core.bingehaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BingeHavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(BingeHavenApplication.class, args);
	}

}
