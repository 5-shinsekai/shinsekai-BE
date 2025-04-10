package com.example.shinsekai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories(basePackages = "com.example.shinsekai")
@EnableJpaAuditing
@ConfigurationPropertiesScan
@SpringBootApplication
public class ShinsekaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShinsekaiApplication.class, args);
	}

}
