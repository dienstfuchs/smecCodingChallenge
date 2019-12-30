package com.smec.codingchallengewebapi.rest.event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

	List<EventDTO> getAllEventsByAccountName(String accountName, LocalDateTime startDate, LocalDateTime endDate);
	
	EventDTO createEvent(EventDTO event, String accountName);

}
