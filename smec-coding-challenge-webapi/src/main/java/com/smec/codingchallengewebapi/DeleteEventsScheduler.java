package com.smec.codingchallengewebapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.rest.event.EventService;

@Component
public class DeleteEventsScheduler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EventService eventService;
	
	public DeleteEventsScheduler() {
	}
	
	@Scheduled(cron = "${deleteeventsscheduler.cron:-}")
	public void deleteEvents() {
		logger.info("Scheduled deleting of events started.");
		eventService.deleteOldEvents();
	}

}
