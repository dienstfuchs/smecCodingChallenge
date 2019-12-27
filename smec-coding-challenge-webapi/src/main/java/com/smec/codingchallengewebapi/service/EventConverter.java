package com.smec.codingchallengewebapi.service;

import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;
import com.smec.codingchallengewebapi.rest.event.EventDTO;

@Component
public class EventConverter {

	public EventDTO toDTO(Event event) {
		if(event == null) {
			return null;
		}
		return new EventDTO(event.getHappenedAt(), event.getType());
	}
	
	public Event toEntity(EventDTO eventDTO, Account account) {
		if(eventDTO == null) {
			return null;
		}
		return new Event(eventDTO.getHappenedAt(), eventDTO.getType(), account);
	}
	
}
