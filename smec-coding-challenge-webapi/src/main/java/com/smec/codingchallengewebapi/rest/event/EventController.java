package com.smec.codingchallengewebapi.rest.event;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping("/accounts/{accountName}/events")
	public List<EventDTO> getAllEvents(@PathVariable String accountName) {
		return eventService.getAllEventsByAccountName(accountName);
	}

	@PostMapping("/accounts/{accountName}/events")
	EventDTO createEvent(@RequestBody EventDTO event, @PathVariable String accountName) {
		return eventService.createEvent(event, accountName);
	}

}
