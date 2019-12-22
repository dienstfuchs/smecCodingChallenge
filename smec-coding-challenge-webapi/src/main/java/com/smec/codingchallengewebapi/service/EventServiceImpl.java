package com.smec.codingchallengewebapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.persistence.EventRepository;
import com.smec.codingchallengewebapi.rest.account.AccountNotFoundException;
import com.smec.codingchallengewebapi.rest.event.EventDTO;
import com.smec.codingchallengewebapi.rest.event.EventService;

@Component
public class EventServiceImpl implements EventService {

	private final AccountRepository accountRepository;
	private final EventRepository eventRepository;

	public EventServiceImpl(AccountRepository accountRepository, EventRepository eventRepository) {
		this.accountRepository = accountRepository;
		this.eventRepository = eventRepository;
	}

	@Override
	public List<EventDTO> getAllEventsByAccountName(String accountName) {
		Account account = accountRepository.findByName(accountName);
		if (account == null) {
			throw new AccountNotFoundException(accountName);
		}
		return eventRepository.findByAccount(account).stream().map(event -> EventConverter.toDTO(event))
				.collect(Collectors.toList());
	}

	@Override
	public EventDTO createEvent(EventDTO eventDTO, String accountName) {
		Account account = accountRepository.findByName(accountName);
		if (account == null) {
			throw new AccountNotFoundException(accountName);
		}
		Event createdEvent = eventRepository.save(EventConverter.toEntity(eventDTO, account));
		return EventConverter.toDTO(createdEvent);
	}

}
