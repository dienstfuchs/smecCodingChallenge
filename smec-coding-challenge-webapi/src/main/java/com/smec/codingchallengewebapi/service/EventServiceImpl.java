package com.smec.codingchallengewebapi.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.persistence.EventRepository;
import com.smec.codingchallengewebapi.rest.account.AccountNotFoundException;
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
	public List<Event> getAllEventsByAccountName(String accountName) {
		Account account = accountRepository.findByName(accountName);
		if (account == null) {
			throw new AccountNotFoundException(accountName);
		}
		return eventRepository.findByAccount(account);
	}

	@Override
	public Event createEvent(Event event) {
		return eventRepository.save(event);
	}

}
