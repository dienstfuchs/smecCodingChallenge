package com.smec.codingchallengewebapi;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

			ExecutorService executor = Executors.newFixedThreadPool(10);

			Future f1 = executor.submit(() -> {
				addEvents(eventService, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_1", "dienstfuchs");
			});
			Future f2 = executor.submit(() -> {
				addEvents(eventService, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_2", "dienstfuchs");
			});
			Future f3 = executor.submit(() -> {
				addEvents(eventService, LocalDateTime.of(2019, 12, 2, 15, 0), "Event_2", "dienstfuchs");
			});
			
			Future f4 = executor.submit(() -> {
				addEvents(eventService, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_1", "magi");
			});
			
			Future f5 = executor.submit(() -> {
				addEvents(eventService, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_2", "magi");
			});
			
			Future f6 = executor.submit(() -> {
				addEvents(eventService, LocalDateTime.of(2019, 12, 2, 15, 0), "Event_2", "magi");
			});

			f1.get();
			f2.get();
			f3.get();
			f4.get();
			f5.get();
			f6.get();
			System.out.println("importing finished");

//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 1"), "dienstfuchs");
//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 1), "Event 1"), "dienstfuchs");
//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 23, 15, 1), "Event 1"), "dienstfuchs");
//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 2), "Event 2"), "dienstfuchs");
//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 4), "Event 2"), "dienstfuchs");
//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 23, 15, 4), "Event 2"), "dienstfuchs");
//
//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 21, 15, 0), "Event 3"), "magi");
//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
//			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");

		};
	}

	private void addEvents(EventService eventService, LocalDateTime dateTime, String eventName, String accountName) {
		for (int i = 0; i < 10000; i++) {
			int a = i % 2;
			eventService.createEvent(new EventDTO(dateTime, eventName + "_" + accountName), accountName);
		}
	}

}
