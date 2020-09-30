package com.pavliuchenko.messageprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableR2dbcRepositories
public class MessageProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageProviderApplication.class, args);
	}

}
