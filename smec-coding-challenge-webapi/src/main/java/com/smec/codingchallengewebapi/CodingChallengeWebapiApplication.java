package com.smec.codingchallengewebapi;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.persistence.EventRepository;

@SpringBootApplication
public class CodingChallengeWebapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingChallengeWebapiApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(AccountRepository accountRepository, EventRepository eventRepository) {
	    return (args) -> {
	    	Account dienstfuchs = new Account("dienstfuchs");
			accountRepository.save(dienstfuchs);
	    	Account magi = new Account("magi");
			accountRepository.save(magi);
	    	
	    	eventRepository.save(new Event(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 1", dienstfuchs));
	    	eventRepository.save(new Event(LocalDateTime.of(2019, 12, 22, 15, 1), "Event 1", dienstfuchs));
	    	eventRepository.save(new Event(LocalDateTime.of(2019, 12, 23, 15, 1), "Event 1", dienstfuchs));
	    	eventRepository.save(new Event(LocalDateTime.of(2019, 12, 22, 15, 2), "Event 2", dienstfuchs));
	    	eventRepository.save(new Event(LocalDateTime.of(2019, 12, 22, 15, 4), "Event 2", dienstfuchs));
	    	eventRepository.save(new Event(LocalDateTime.of(2019, 12, 23, 15, 4), "Event 2", dienstfuchs));
	    	
	    	eventRepository.save(new Event(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3", magi));
	    };
	}

}
