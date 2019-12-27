package com.smec.codingchallengewebapi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger logger = LoggerFactory.getLogger(CodingChallengeWebapiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CodingChallengeWebapiApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner loadData(AccountService accountService, EventService eventService) {
//		return (args) -> {
//			AccountDTO dienstfuchs = new AccountDTO("dienstfuchs");
//			accountService.createAccount(dienstfuchs);
//			AccountDTO magi = new AccountDTO("magi");
//			accountService.createAccount(magi);
//
//			ExecutorService executor = Executors.newFixedThreadPool(10);
//
//			logger.info("importing data started");
//
//			List<Future> futures = new ArrayList<Future>();
//
//			futures.add(executor.submit(() -> {
//				addEvents(eventService, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_1", "dienstfuchs");
//			}));
//
//			futures.add(executor.submit(() -> {
//				addEvents(eventService, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_2", "dienstfuchs");
//			}));
//
//			futures.add(executor.submit(() -> {
//				addEvents(eventService, LocalDateTime.of(2019, 12, 2, 15, 0), "Event_2", "dienstfuchs");
//			}));
//
//			futures.add(executor.submit(() -> {
//				addEvents(eventService, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_1", "magi");
//			}));
//
//			futures.add(executor.submit(() -> {
//				addEvents(eventService, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_2", "magi");
//			}));
//
//			futures.add(executor.submit(() -> {
//				addEvents(eventService, LocalDateTime.of(2019, 12, 2, 15, 0), "Event_2", "magi");
//			}));
//
//			for (Future f : futures) {
//				f.get();
//			}
//
//			logger.info("importing data finished");
//		};
//	}
//
//	private void addEvents(EventService eventService, LocalDateTime dateTime, String eventName, String accountName) {
//		for (int i = 0; i < 10000; i++) {
//			eventService.createEvent(new EventDTO(dateTime, eventName + "_" + accountName), accountName);
//		}
//	}

}
