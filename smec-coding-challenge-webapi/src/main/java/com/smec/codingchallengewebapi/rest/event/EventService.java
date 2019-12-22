package com.smec.codingchallengewebapi.rest.event;

import java.util.List;

import com.smec.codingchallengewebapi.entities.Event;

public interface EventService {

	List<Event> getAllEventsByAccountName(String accountName);
	
	Event createEvent(Event event);

}
