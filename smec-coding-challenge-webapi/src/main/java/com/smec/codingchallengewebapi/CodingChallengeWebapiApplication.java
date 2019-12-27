package com.smec.codingchallengewebapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodingChallengeWebapiApplication {

	Logger logger = LoggerFactory.getLogger(CodingChallengeWebapiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CodingChallengeWebapiApplication.class, args);
	}

////	@Bean
////	public CommandLineRunner loadData(AccountService accountService, EventService eventService) {
////		return (args) -> {
////			AccountDTO dienstfuchs = new AccountDTO("dienstfuchs");
////			accountService.createAccount(dienstfuchs);
////			AccountDTO magi = new AccountDTO("magi");
////			accountService.createAccount(magi);
////
////			ExecutorService executor = Executors.newFixedThreadPool(10);
////
////			logger.info("importing data started");
////
////			List<Future> futures = new ArrayList<Future>();
////			
////			futures.add(executor.submit(() -> {
////				addEvents(eventService, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_1", "dienstfuchs");
////			}));
////
////			futures.add(executor.submit(() -> {
////				addEvents(eventService, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_2", "dienstfuchs");
////			}));
////
////			futures.add(executor.submit(() -> {
////				addEvents(eventService, LocalDateTime.of(2019, 12, 2, 15, 0), "Event_2", "dienstfuchs");
////			}));
////
////			futures.add(executor.submit(() -> {
////				addEvents(eventService, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_1", "magi");
////			}));
////
////			futures.add(executor.submit(() -> {
////				addEvents(eventService, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_2", "magi");
////			}));
////
////			futures.add(executor.submit(() -> {
////				addEvents(eventService, LocalDateTime.of(2019, 12, 2, 15, 0), "Event_2", "magi");
////			}));
////
////			for (Future f : futures) {
////				f.get();
////			}
////
////			logger.info("importing data finished");
////
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 1"), "dienstfuchs");
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 1), "Event 1"), "dienstfuchs");
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 23, 15, 1), "Event 1"), "dienstfuchs");
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 2), "Event 2"), "dienstfuchs");
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 4), "Event 2"), "dienstfuchs");
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 23, 15, 4), "Event 2"), "dienstfuchs");
//////
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 21, 15, 0), "Event 3"), "magi");
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
//////			eventService.createEvent(new EventDTO(LocalDateTime.of(2019, 12, 22, 15, 0), "Event 3"), "magi");
////
////		};
////	}
//
//	private void addEvents(EventService eventService, LocalDateTime dateTime, String eventName, String accountName) {
//		for (int i = 0; i < 10000; i++) {
//			eventService.createEvent(new EventDTO(dateTime, eventName + "_" + accountName), accountName);
//		}
//	}

}
