package com.smec.codingchallengewebapi.rest.event;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping("/accounts/{accountName}/events")
	public List<EventDTO> getAllEvents(@PathVariable String accountName,
			@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {
		return eventService.getAllEventsByAccountName(accountName, startDate, endDate);
	}

	@PostMapping("/accounts/{accountName}/events")
	@ResponseStatus(code = HttpStatus.CREATED)
	EventDTO createEvent(@RequestBody EventDTO event, @PathVariable String accountName) {
		return eventService.createEvent(event, accountName);
	}

}
