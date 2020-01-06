package com.smec.codingchallengewebapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CodingChallengeWebapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingChallengeWebapiApplication.class, args);
	}

}
