package com.smec.codingchallengewebapi.rest.event;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping("/accounts/{accountName}/events")
	public List<EventDTO> getAllEvents(@PathVariable String accountName,
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime endDate) {
		logger.info("getAllEvents() executed");
		return eventService.getAllEventsByAccountName(accountName, startDate, endDate);
	}

	@PostMapping("/accounts/{accountName}/events")
	@ResponseStatus(code = HttpStatus.CREATED)
	EventDTO createEvent(@RequestBody EventDTO event, @PathVariable String accountName) {
		logger.debug("createEvent(Account: '{}', Event: '{}') executed", accountName, event);
		return eventService.createEvent(event, accountName);
	}
	
	@DeleteMapping("/accounts/deleteOldEvents")
	@ResponseStatus(code = HttpStatus.OK)
	void deleteOldEvents() {
		logger.info("deleteOldEvents() executed");
		eventService.deleteOldEvents();
	}

}
