package com.smec.codingchallengewebapi;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.smec.codingchallengewebapi.rest.account.AccountDTO;
import com.smec.codingchallengewebapi.rest.account.AccountService;
import com.smec.codingchallengewebapi.rest.event.EventDTO;
import com.smec.codingchallengewebapi.rest.event.EventService;

@SpringBootApplication
public class CodingChallengeWebapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingChallengeWebapiApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(AccountService accountService, EventService eventService) {
	    return (args) -> {
	    	AccountDTO dienstfuchs = new AccountDTO("dienstfuchs");
	    	accountService.createAccount(dienstfuchs);
	    	AccountDTO magi = new AccountDTO("magi");
	    	accountService.createAccount(magi);
	    	
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 1"), "dienstfuchs");
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 1), "Event 1"), "dienstfuchs");
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 23, 15, 1), "Event 1"), "dienstfuchs");
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 2), "Event 2"), "dienstfuchs");
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 4), "Event 2"), "dienstfuchs");
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 23, 15, 4), "Event 2"), "dienstfuchs");
	    	
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 21, 15, 0), "Event 3"), "magi");
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
	    	eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
	    };
	}

}
