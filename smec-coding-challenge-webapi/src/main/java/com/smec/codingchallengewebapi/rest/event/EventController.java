package com.smec.codingchallengewebapi.rest.event;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smec.codingchallengewebapi.entities.Event;

@RestController
public class EventController {

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping("/accounts/{accountName}/events")
	public List<Event> getAllEvents(@PathVariable String accountName) {
		return eventService.getAllEventsByAccountName(accountName);
	}

	@PostMapping
	Event createEvent(@RequestBody Event event) {
		return eventService.createEvent(event);
	}

}
