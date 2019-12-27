package com.smec.codingchallengewebapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.persistence.EventRepository;
import com.smec.codingchallengewebapi.rest.event.EventDTO;
import com.smec.codingchallengewebapi.rest.event.EventService;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsService;

@Component
public class EventServiceImpl implements EventService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private EventConverter eventConverter;
	
	public EventServiceImpl() {
	}

	@Override
	public List<EventDTO> getAllEventsByAccountName(String accountName) {
		Account account = accountRepository.findAccountByNameOrThrow(accountName);
		return eventRepository.findByAccountName(account.getName()).stream().map(event -> eventConverter.toDTO(event))
				.collect(Collectors.toList());
	}

	@Override
	public EventDTO createEvent(EventDTO eventDTO, String accountName) {
		Account account = accountRepository.findAccountByNameOrThrow(accountName);
		Event createdEvent = eventRepository.save(eventConverter.toEntity(eventDTO, account));
		EventDTO createdEventDTO = eventConverter.toDTO(createdEvent);
		statisticsService.createStatisticsForEvent(createdEventDTO, account);
		return createdEventDTO;
	}
	
}
