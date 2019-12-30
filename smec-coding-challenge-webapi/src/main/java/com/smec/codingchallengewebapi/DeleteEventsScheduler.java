package com.smec.codingchallengewebapi;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.persistence.EventRepository;

@Component
public class DeleteEventsScheduler {

	private static final int DAYS = 30;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EventRepository eventRepostiory;
	
	public DeleteEventsScheduler() {
		System.out.println("test");
	}
	
	@Scheduled(cron = "${deleteeventsscheduler.cron:-}")
	public void deleteEvents() {
		logger.info("Scheduled deleting of events started.");
		eventRepostiory.deleteEventsBefore(LocalDateTime.now().minusDays(DAYS));
	}

}
