package com.smec.codingchallengewebapi.rest.event;

import java.util.List;

public interface EventService {

	List<EventDTO> getAllEventsByAccountName(String accountName);
	
	EventDTO createEvent(EventDTO event, String accountName);

}
