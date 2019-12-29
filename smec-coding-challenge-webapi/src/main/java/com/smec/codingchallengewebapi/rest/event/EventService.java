package com.smec.codingchallengewebapi.rest.event;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

	List<EventDTO> getAllEventsByAccountName(String accountName, LocalDate startDate, LocalDate endDate);
	
	EventDTO createEvent(EventDTO event, String accountName);

}
