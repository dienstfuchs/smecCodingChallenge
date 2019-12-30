package com.smec.codingchallengewebapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CodingChallengeWebapiApplication {

	Logger logger = LoggerFactory.getLogger(CodingChallengeWebapiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CodingChallengeWebapiApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner loadData(AccountService accountService, EventService eventService) {
//		return (args) -> {
//			for (int i = 0; i < 100; i++) {
//				accountService.createAccount(new AccountDTO("Account" + i));
//			}
//
//			ExecutorService executor = Executors.newFixedThreadPool(10);
//
//			logger.info("importing data started");
//
//			List<Future> futures = new ArrayList<Future>();
//			for (int i = 0; i < 100; i = i + 1) {
//				futures.add(create(executor, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_1", eventService, i));
//				futures.add(create(executor, LocalDateTime.of(2019, 12, 1, 15, 0), "Event_2", eventService, i));
//				futures.add(create(executor, LocalDateTime.of(2019, 12, 2, 15, 0), "Event_2", eventService, i));
//			}
//
//			for (Future f : futures) {
//				f.get();
//			}
//
//			logger.info("importing data finished");
//		};
//	}
//
//	private Future create(ExecutorService executor, LocalDateTime dateTime, String eventName, EventService eventService,
//			final int i) {
//		return executor.submit(() -> {
//			addEvents(eventService, dateTime, eventName, "Account" + i);
//		});
//	}
//
//	private void addEvents(EventService eventService, LocalDateTime dateTime, String eventName, String accountName) {
//		for (int i = 0; i < 10000; i++) {
//			eventService.createEvent(new EventDTO(dateTime, eventName + "_" + accountName), accountName);
//		}
//	}

}
