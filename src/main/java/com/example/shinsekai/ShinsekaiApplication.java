package com.example.shinsekai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShinsekaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShinsekaiApplication.class, args);
	}

}
