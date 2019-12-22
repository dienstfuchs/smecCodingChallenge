package com.smec.codingchallengewebapi.service;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;
import com.smec.codingchallengewebapi.rest.event.EventDTO;

public class EventConverter {

	public static EventDTO toDTO(Event event) {
		return new EventDTO(event.getHappenedAt(), event.getType());
	}
	
	public static Event toEntity(EventDTO eventDTO, Account account) {
		return new Event(eventDTO.getHappenedAt(), eventDTO.getType(), account);
	}
	
}
