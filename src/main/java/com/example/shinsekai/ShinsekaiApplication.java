package com.example.shinsekai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

//@EnableJpaRepositories(basePackages = "com.example.shinsekai")
@EnableJpaAuditing
@ConfigurationPropertiesScan
@SpringBootApplication
public class ShinsekaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShinsekaiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
